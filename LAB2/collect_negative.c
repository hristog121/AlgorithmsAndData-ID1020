
/**
 * Implement a function in C which takes an array of integers (both positive and negative) 
 * and orders the elements in the array so that all negative elements come before the positive. 
 * You are not allowed to sort the array - only collect all negative values first. 
 * The algorithm should only use O(1) extra memory (i.e. be in-place)
 * @author Hristo Georgiev - 1c3r00t
 * */

#include <stdio.h>

/**
 * Method to intercept the negative numbers and put the on the "left"
 * side of the array (the lowest index in the array)
 * */
void separate_insertion(int input_array[], int n)
{   
    //
    int currentIndex, negativeValues = 0;
    for (currentIndex = 0; currentIndex < n; currentIndex++)
    {   
        //Checks if the value at the current index is less than 0 (negative) 
        if (input_array[currentIndex] < 0)
        {   
            //Check if the current index not equal negative Value and do the swaping of the elements moving all negative elements to the lower indecies in the array
            if (currentIndex != negativeValues)
            {
                int temp = input_array[currentIndex];
                input_array[currentIndex] = input_array[negativeValues];
                input_array[negativeValues] = temp;
            }
            //Move negativeValue one position to the right
            negativeValues++;
        }
    }
}

/**
 * Main method to test the functionallity of the function
 **/

int main(void)
{
    int input;
    printf("Enter the size of the array: ");
    // takes in the first number as the length of the array
    scanf("%d", &input);
    int input_array[input];
    // takes in remaining numbers as part of the array in the format (-x y -y x)
    printf("Enter the elements that will go into the array with space intebween them (Both positive and negative values): ");
    for (int i = 0; i < input; i++)
    {
        scanf("%d", &input_array[i]);
    }
    separate_insertion(input_array, input);

    printf("Result:\n");
    // prints the segregated array
    for (int i = 0; i < input; i++)
    {
        printf("%d ", input_array[i]);
        
    }
    printf("\n");
    printf("If the given input is: 6 -5 1 -4\n ");
    printf("The expected output is: -5 -4 1 6 9\n");
}