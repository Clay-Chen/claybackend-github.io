package com.clay.uesrcenter;

public class terr {
    public static void main(String[] args) {
        int num[] = {5,7,7,8,8,10};
        int i = search(num,8);
        System.out.println(i);
    }
    public static int search(int[] nums, int target) {
        int leftdx = binarySearch( nums, target ,true);
        int rightdx = binarySearch(nums,target,false)-1;
        if(leftdx <= rightdx && rightdx < nums.length && nums[leftdx] == target && nums[rightdx] == target){
            return rightdx-leftdx+1;
        }
        return 0 ;
    }


    public static int binarySearch(int[] nums,int target,boolean lower){
        int left = 0; int right = nums.length-1,ans = nums.length;
        while(left <= right){
            int mid = (left+right)/2;
            if(nums[mid] > target || (lower && nums[mid] >= target)){
                right = mid - 1;
                ans = mid;
            }
            else{
                left =mid + 1;
            }
        }
        return ans;
    }
}
