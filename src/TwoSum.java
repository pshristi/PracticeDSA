import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args) {
        int[] nums = {3,2,4};
        int target = 6;
        for(int i : new TwoSum().twoSum(nums, target)) {
            System.out.println(i);
        }
    }
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, List<Integer>> m = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (!m.containsKey(nums[i])) {
                m.put(nums[i], new ArrayList());
            }
            List<Integer> currIdx = m.get(nums[i]);
            currIdx.add(i);
            m.put(nums[i], currIdx);
        }

        for (int i = 0; i < nums.length; i++) {
            Integer check = target - nums[i];
            if (m.containsKey(check)) {
                if (check == nums[i] && m.get(check).size() > 1) {
                    int idx = m.get(check).get(1);
                    return new int[] { i, idx };
                } else if (check != nums[i]) {
                    int idx = m.get(check).get(0);
                    return new int[] { i, idx };
                }
            }
        }
        return new int[0];
    }
}
