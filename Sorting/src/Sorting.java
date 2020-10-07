import java.util.Random;
import Plotter.Plotter;

public class Sorting {

	final static int SMALL_DEMO_ARRAY = 100000;
	final static int BIG_DEMO_ARRAY = 100000000;
	final static int BUILDHEAP_VS_QUICK_LENGTH = 15;
	final static int MERGE_VS_QUICK_LENGTH = 16;
	final static int MERGE_VS_QUICK_SORTED_LENGTH = 12;
	final static int HEAP_VS_BUBBLE_LENGTH = 12;
	final static double T = 600.0;
	/**
	 * Sorts a given array using the quick sort algorithm.
	 * At each stage the pivot is chosen to be the righttmost element of the subarray.
	 * 
	 * Should run in average complexity of O(nlog(n)), and worst case complexity of O(n^2)
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void quickSort(double[] arr){
		recQuickSort(arr, 0 , arr.length - 1);
	}

	private static void recQuickSort(double[] arr, int p, int r){
		if(p < r){
			int q = partition(arr, p, r);
			recQuickSort(arr, p, q - 1);
			recQuickSort(arr, q + 1, r);
		}
	}

	private static int partition(double[] arr, int p, int r){
		double x = arr[r];
		int i = p - 1;
		for(int j = p; j < r; j++) {
			if(arr[j] <= x){
				i = i + 1;
				double temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
		double temp = arr[i + 1];
		arr[i + 1] = arr[r];
		arr[r] = temp;
		return i + 1;
	}



	/**
	 * Sorts a given array using the merge sort algorithm.
	 * 
	 * Should run in complexity O(nlog(n)) in the worst case.
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void mergeSort(double[] arr){
		recMergeSort(arr, 0, arr.length-1);
	}

	private static void recMergeSort(double[] arr, int p, int r){
		if(p < r){
			int q = (p + r) / 2;
			recMergeSort(arr, p, q);
			recMergeSort(arr,q + 1, r);
			merge(arr, p, q, r);
		}
	}

	private static void merge(double[] arr, int p,int q, int r){
		double [] lArr = new double[q - p + 2];
		double [] rArr = new double[r - q + 1];
		for (int i = 0; i < lArr.length - 1; i++){
			lArr[i] = arr[p+ i];
		}
		for (int i = 0; i < rArr.length - 1; i++){
			rArr[i] = arr[q + i + 1];
		}
		lArr[lArr.length - 1] =  Integer.MAX_VALUE;
		rArr[rArr.length - 1] =  Integer.MAX_VALUE;

		int i = 0;
		int j = 0;
		for(int k = p; k <= r; k++){
			if(lArr[i] < rArr[j]) {
				arr[k] = lArr[i];
				i++;
			}
			else {
				arr[k] = rArr[j];
				j++;
			}
		}
	}
	/**
	 * Builds a max-heap from the given array.
	 * That is, when using the resulting array to represent an almost complete binary tree
	 * The value of each node must be larger (or equal) to the value of any of its descendants.
	 * Should run in complexity O(n) in the worst case.
	 * 
	 * @param arr - the given array.
	 */
	public static void buildHeap(double[] arr){
		for(int i = arr.length / 2; i>= 0 ; i--){
			percDown(arr, arr[i], i ,arr.length -1);
		}
	}

	public static void percDown(double[] arr, double x, int i, int n){
		if(2*i + 1 > n){
			arr[i] = x;
		}
		else if(2*i + 1 == n) {
			if (arr[2*i + 1] > x) {
				arr[i] = arr[2 * i + 1];
				arr[2*i + 1] = x;
			} else {
				arr[i] = x;
			}
		}
		else {
			int j;
			if (arr[2*i + 1] > arr[2*i + 2]){
				j = 2*i + 1;
			}
			else {
				j = 2*i + 2;
			}
			if(arr[j] > x){
				arr[i] = arr[j];
				percDown(arr, x, j, n);
			}
			else{
				arr[i] = x;
			}
		}
	}

    
	/**
	 * Sorts a given array using the heap sort algorithm.
	 * 
	 * Should run in complexity O(nlog(n)) in the worst case.
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void heapSort(double[] arr){
		buildHeap(arr);
		for (int i = arr.length - 1; i > 0; i--){
			double temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;
			percDown(arr, arr[0],0, i -1);
		}
	}	
		
	/**
	 * Sorts a given array using the bubble sort algorithm.
	 * 
	 * Should run in complexity O(n^2) in the worst case.
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void bubbleSort(double[] arr){
		for(int i = 1; i < arr.length; i++){
			if(arr[i] < arr[i - 1]) {
				bubbleDown(arr, i);
			}
		}
	}

	private static void bubbleDown(double[] arr, int i) {
		for (int j= i; j > 0; j--){
			if(arr[j] < arr[j - 1]){
				double temp = arr[j];
				arr[j] = arr[j - 1];
				arr[j - 1] = temp;
			}
			else {
				return;
			}
		}
	}

	public static void main(String[] args) {
		buildHeapVsQuick();
		mergeVsQuick();
		mergeVsQuickOnSortedArray();
		heapSortVsBubble();
	}

	/**
	 * Compares the build heap algorithm against quick sort on random arrays
	 */
	public static void buildHeapVsQuick(){
		double[] quickTimes = new double[BUILDHEAP_VS_QUICK_LENGTH];
		double[] heapTimes = new double[BUILDHEAP_VS_QUICK_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < BUILDHEAP_VS_QUICK_LENGTH; i++) {
			long sumQuick = 0;
			long sumHeap = 0;
			for(int k = 0; k < T; k++){
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				buildHeap(b);
				endTime = System.currentTimeMillis();
				sumHeap += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			heapTimes[i] = sumHeap/T;
		}
		Plotter.plot("quick sort", quickTimes, "build heap", heapTimes);
	}
	
	/**
	 * Compares the merge sort algorithm against quick sort on random arrays
	 */
	public static void mergeVsQuick(){
		double[] quickTimes = new double[MERGE_VS_QUICK_LENGTH];
		double[] mergeTimes = new double[MERGE_VS_QUICK_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < MERGE_VS_QUICK_LENGTH; i++) {
			long sumQuick = 0;
			long sumMerge = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				mergeSort(b);
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			mergeTimes[i] = sumMerge/T;
		}
		Plotter.plot("quick sort", quickTimes, "merge sort", mergeTimes);
	}

	/**
	 * Compares the merge sort algorithm against quick sort on pre-sorted arrays
	 */
	public static void mergeVsQuickOnSortedArray(){
		double[] quickTimes = new double[MERGE_VS_QUICK_SORTED_LENGTH];
		double[] mergeTimes = new double[MERGE_VS_QUICK_SORTED_LENGTH];
		long startTime, endTime;
		for (int i = 0; i < MERGE_VS_QUICK_SORTED_LENGTH; i++) {
			long sumQuick = 0;
			long sumMerge = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = j;
					b[j] = j;
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				mergeSort(b);
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
			}
			quickTimes[i] = sumQuick/T;
			mergeTimes[i] = sumMerge/T;
		}
		Plotter.plot("quick sort on sorted array", quickTimes, "merge sort on sorted array", mergeTimes);
	}

	/**
	 * Compares the bubble sort algorithm against heap sort.
	 */
	public static void heapSortVsBubble(){
		double[] bubbleTimes = new double[HEAP_VS_BUBBLE_LENGTH];
		double[] heapTimes = new double[HEAP_VS_BUBBLE_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < HEAP_VS_BUBBLE_LENGTH; i++) {
			long sumBubble = 0;
			long sumHeap = 0;
			for (int k = 0; k < T; k++) {
				int size = (int)Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				bubbleSort(a);
				endTime = System.currentTimeMillis();
				sumBubble += endTime - startTime;
				startTime = System.currentTimeMillis();
				heapSort(b);
				endTime = System.currentTimeMillis();
				sumHeap += endTime - startTime;
			}
			bubbleTimes[i] = sumBubble/T;
			heapTimes[i] = sumHeap/T;
		}
		Plotter.plot("bubble sort", bubbleTimes, "heap sort", heapTimes);
	}
}
