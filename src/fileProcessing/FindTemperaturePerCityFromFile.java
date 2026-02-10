package fileProcessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FindTemperaturePerCityFromFile {
    /***
     * Input file with each line having a city code and temperature of the city in celsius, separated by a comma
     *
     * E.g Input
     * DL, 30
     * BOM, 32
     * DL, 31
     * CCU, 29
     * HYD, 25
     * DL, 32
     * DL, 20
     *
     * Output:
     * DL: 30/31/30.5
     * BOM:32/32/32
     *
     * Return min, max and average temperature of each city in the file
     * File can have millions of lines in it but the program will be running on a machine with 4 CPU cores
     *
     * Assumptions:
     * Max 100 cities will be present in the file
     * File is not sorted based on either city or temperature
     */

    private static final int MAX_THREADS = 4;
    private static final int BATCH_SIZE = 10000;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
    private static final ConcurrentHashMap<String, TemperatureStats> statsMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try {
            getStatsPerCity("~/Work1/Projects/PracticeDSA/src/fileProcessing/test.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getStatsPerCity(String filePath) throws Exception {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(Path.of(filePath).toFile()));
            String line = br.readLine();
            List<String> batch = new ArrayList<>();
            while (line != null) {
                batch.add(line);
                if(batch.size() >= BATCH_SIZE) {
                    List<String> chunks = new ArrayList<>(batch);
                    executorService.submit( () -> {
                        processChunk(chunks);
                    });
                    batch.clear();
                }
                line = br.readLine();
            }
            if(!batch.isEmpty()) {
                executorService.submit( () -> {
                    processChunk(batch);
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(br != null) {
                br.close();
            }
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.HOURS);
        }
        statsMap.forEach((city, stats) -> System.out.println(city + ":" + stats.min + "/" + stats.max + "/" + String.format("%.1f", stats.sum / stats.count) ));
    }

    private static void processChunk(List<String> chunks) {

        for (String line : chunks) {
            String[] cityTemp = line.split(",");
            if (cityTemp.length != 2) continue;
            String city = cityTemp[0].trim();
            double temp;
            try {
                temp = Double.parseDouble(cityTemp[1].trim());
            } catch (NumberFormatException e) {
                continue;
            }
            statsMap.compute(city, (k, existing) -> {
                if (existing == null) existing = new TemperatureStats();
                existing.updateStats(temp);
                return existing;
            });
        }
    }

    public static class TemperatureStats {
        private double min;
        private double max;
        private double sum;
        private int count;

        public TemperatureStats() {
            this.min = Double.POSITIVE_INFINITY;
            this.max = Double.NEGATIVE_INFINITY;
            this.sum = 0.0;
            this.count = 0;
        }

        public void updateStats(double temp) {
            this.min = Math.min(min, temp);
            this.max = Math.max(max, temp);
            this.sum += temp;
            this.count++;
        }
    }
}
