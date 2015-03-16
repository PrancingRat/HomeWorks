package work_1;

import java.util.Arrays;
import java.util.Random;

import utils.Timer;

public class LSDSort {
  // ----------------------------------------------------------------------
  private static final int MAX = 1000000;
  private static final int WIDTH = 8;

  // ----------------------------------------------------------------------
  public static void main(String[] args) {

    Timer t = new Timer();
    int[] data = generate(); // Generate test sequence.
    /*
     * Sorting methods always change initial sequence, so we need temporary
     * array to run with the same data.
     */
    int[] test = new int[data.length];

    /*
     * Testing standard "Arrays.sort" algorithm.
     */
    System.arraycopy( data, 0, test, 0, test.length );
    t.start();
    Arrays.sort( data );
    reportTime( "Standart", t.getElapsedTimeMS() );

    /*
     * Testing "LSD" algorithm.
     */
    System.arraycopy( data, 0, test, 0, test.length );
    t.start();
    if ( doLSDSort( data, LSDSort.WIDTH ) != 0 ) {
      System.out.println( "Current width just too high: " + LSDSort.WIDTH );
    } else {
      reportTime( "LSD", t.getElapsedTimeMS() );
    }
  }

  // ----------------------------------------------------------------------
  private static void reportTime(String alg, long time) {
    System.out.println( "Results for \"" + alg + "\": " + time + "ms" );
  }

  // ----------------------------------------------------------------------
  private static int[] generate() {
    int[] data = new int[LSDSort.MAX];

    Random random = new Random();

    for ( int i = 0; i < data.length; i++ ) {
      data[i] = random.nextInt( LSDSort.MAX ); // 0 - 999999
    }

    return data;
  }

  // ----------------------------------------------------------------------
  private static int doLSDSort(int[] data, int width) {
    int isz = Integer.SIZE;
    int size = 1 << width;
    int[] result = null;
    int[] count = null;
    int[] pref = null;
    try {
      result = new int[data.length];
      count = new int[size];
      pref = new int[size];
    } catch ( OutOfMemoryError err ) {
      return -1;
    }
    int groups = (int)Math.ceil( (double)isz / width );
    int mask = ( 1 << width ) - 1;
    for ( int c = 0, shift = 0; c < groups; c++, shift += width ) {
      for ( int j = 0; j < count.length; j++ ) {
        count[j] = 0;
      }
      for ( int i = 0; i < data.length; i++ ) {
        count[( data[i] >> shift ) & mask]++;
      }
      pref[0] = 0;
      for ( int i = 1; i < count.length; i++ ) {
        pref[i] = pref[i - 1] + count[i - 1];
      }
      for ( int i = 0; i < data.length; i++ ) {
        result[pref[( data[i] >> shift ) & mask]++] = data[i];
      }
      System.arraycopy( result, 0, data, 0, data.length );
    }
    return 0;
  }
  // ----------------------------------------------------------------------
}
