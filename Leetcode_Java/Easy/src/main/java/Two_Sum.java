public class Two_Sum {
    public int[] twoSum(int[] nums, int target) {
        int[] inds = new int[2];
        l1: for(int i = 0; i < nums.length-1; i ++) {
            inds[0] = i;
            for(int j = i+1; j < nums.length; j ++) {
                if (nums[i] + nums[j] == target) {
                    inds[1] = j;
                    break l1;
                }
            }
        }
        return inds;
    }
}
