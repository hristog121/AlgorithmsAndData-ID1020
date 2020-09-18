
/**
 * Implement a function in C which takes an array of integers (both positive and negative) 
 * and orders the elements in the array so that all negative elements come before the positive. 
 * You are not allowed to sort the array - only collect all negative values first. 
 * The algorithm should only use O(1) extra memory (i.e. be in-place)
 * @author Hristo Georgiev - 1c3r00t
 * */

#include <stdio.h>

//Method to intercept the negative numbers and put the on the "left"
//side of the array (the lowest index in the array)

void separate_insertion(int input_array[], int n)
{
    int currentIndex, negativeValues = 0;
    for (currentIndex = 0; currentIndex < n; currentIndex++)
    {
        if (input_array[currentIndex] < 0)
        {
            if (currentIndex != negativeValues)
            {
                int temp = input_array[currentIndex];
                input_array[currentIndex] = input_array[negativeValues];
                input_array[negativeValues] = temp;
            }
            negativeValues++;
        }
    }
}

//TODO Change the test method to take input from the keyboard.
//That will look better

int main(void)
{
    int input;
    printf("Enter the size of the array: ");
    // takes in the first number as the length of the array
    scanf("%d", &input);
    int input_array[input];
    // takes in remaining numbers as part of the array
    printf("Enter the elements that will go into the array (Both positive and negative values): ");
    for (int i = 0; i < input; i++)
    {
        scanf("%d", &input_array[i]);
    }
    separate_insertion(input_array, input);

    printf("Result:\n");
    // prints the segregated array
    for (int i = 0; i < input; i++)
    {
        printf("%d\n", input_array[i]);
    }
}