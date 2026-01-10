class ArrayProductLessThanK {
    public static void main(String[] args) {
        int[] nums = {10, 5, 2, 6};
        int k = 100;
        System.out.println(new ArrayProductLessThanK().numSubarrayProductLessThanK(nums, k));
    }
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        Integer num = 0;
        Integer left = 0;
        Integer prod = 1;

        for(Integer right = left;right < nums.length;right++) {
            prod *= nums[right];
            while(prod >= k) {
                prod/= nums[left];
                left++;
            }
            num += right - left + 1;
        }
        return num;
    }
}
