class AddTwoBinaryInt {
    public String addBinary(String a, String b) {
        int i = a.length();
        int j = b.length();
        int carry = 0;
        StringBuilder r = new StringBuilder();

        while(i >= 0 || j >= 0 || carry != 0) {
            int sum = carry;

            if(i >= 0) {
                sum += a.charAt(i) - '0';
            }

            if(j >= 0) {
                sum += b.charAt(j) - '0';
            }

            r = r.append(sum%2);
            carry = sum/2;
        }

        r = r.reverse();
        String regex = "^0+?!$";
        r.toString().replaceAll(regex, "");
        if(r.toString().isEmpty()) return "0";
        return r.toString();
    }
}
