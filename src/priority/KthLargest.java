package priority;

import java.util.PriorityQueue;

class KthLargest {
    private PriorityQueue<Integer> kTopQueue;
    private int k;

    public KthLargest(int k, int[] nums) {
        this.kTopQueue = new PriorityQueue<>(k + 1);
        this.k = k;

        // initialize
        for (int i = 0; i < k && i < nums.length; i++) {
            this.kTopQueue.offer(nums[i]);
        }

        for (int i = k; i < nums.length; i++) {
            this.add(nums[i]);
        }
    }

    public int add(int val) {
        kTopQueue.add(val);
        if (kTopQueue.size() < k) {
            return -1;
        }
        if (kTopQueue.size() > k) {
            // remove the first element to retain the k top elements
            kTopQueue.poll();
        }
        return kTopQueue.peek();
    }

    public static void main(String[] args) {
        int[] nums = {4, 5, 8, 2};
        KthLargest kthLargest = new KthLargest(3, nums);
        kthLargest.add(3);
        kthLargest.add(5);
    }
}