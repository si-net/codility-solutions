package org.si;

/**
 * The challenge can be found here: https://app.codility.com/programmers/challenges/palladium2020/
 *
 *  The goal of this challenge is to find the smallest (two) area(s) which will cover the buildings of the array H.
 *      The size of the coverage is defined as the highest point (building) within this area multiplied with the
 *      number of elements (buildings) covered by the area.
 *
 *  Problem solution:
 *
 *      To find the solution we must imagine every possible distribution of the left and right area within the given
 *      array  H= | 5, 3, 5, 2, 1 | these would be:
 *          1. left area = 6 (all) buildings
 *          2. left area = 5, right area = 1
 *          3. left area = 4, right area = 2
 *          4. left area = 3, right area = 3
 *          ....
 *
 *      To be able to calculate all of these we must first know which is the highest point within the mentioned areas.
 *      This we achieve by iterating from both the left and right end of the array and setting:
 *          leftMax  [current size of left area]  = max( leftMax[previous size] , H[current size of left area] )
 *          rightMax [current size of right area] = max( rightMax[previous size] , H[current size of right area] )
 *
 *      Now, for our array   H = | 5, 3, 5, 2, 1 |
 *      we have:       leftMax ->| 5, 5, 5, 5, 5 |
 *                               | 5, 5, 5, 2, 1 | <- rightMax
 *
 *      Assuming now that our left coverage is covering 1 building and our right building is covering 5
 *      we just need to look into leftMax[0] and rightMax[1]. If we do this for all valid combinations of coverages we
 *      find the minimal possible coverage with a complexcity of N*2 = O(n)
 */
public class PalladiumChallenge {

    public int solution(int[] H) {

        int N = H.length;

        int[] maxOfLeftCover = new int[N];
        int[] maxOfRightCover = new int[N];

        // Since the both areas MUST touch either boundary of the array, we can initalize them to the value of the boundaries
        maxOfLeftCover[0] = H[0];
        maxOfRightCover[N-1] = H[N-1];

        // loop interating the array from the left and the right border. While skipping the first entry!
        for ( int left = 1, right = N-2; left < H.length && right >= 0; left++, right-- ){

            maxOfLeftCover[left] = Math.max( maxOfLeftCover[left-1], H[left]);

            maxOfRightCover[right] = Math.max( maxOfRightCover[right +1], H[right]);
        }

        // We initalize the minimal required cover to the biggest possible cover, which is one cover for everything.
        int minimalRequiredArea = maxOfRightCover[0] * N;
        // to determine the minimal coverage we must check all other valid coverages.
        for (int i = 1; i < H.length; i++) {
            final int sizeOfLeftCover = maxOfLeftCover[i-1] * i;
            final int sizeOfRightCover = maxOfRightCover[i] * (N-i);
            minimalRequiredArea = Math.min(minimalRequiredArea, sizeOfLeftCover + sizeOfRightCover);
        }
        return minimalRequiredArea;
    }
}
