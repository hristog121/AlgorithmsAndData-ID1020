#include <stdio.h>

int main()
{
    int count = 0;                                                                       //Track number of chars read
    int incoming_chars[8];                                                               //Array to hold the incoming chars from stdin
    int incoming_chars_size = (int)(sizeof(incoming_chars) / sizeof(incoming_chars[0])); //This is the lenghts of the array. This value will be compared with the value of count

    do
    {
        incoming_chars[count] = getchar(); //read in char
        count++;                           //update char count
        if (count >= incoming_chars_size)
        {   
            return printf("The limit of characters is 42. You have exceeded this limit!");
        }
        

    } while (incoming_chars[count - 1] != '\n'); //check if last character read was a newline

    for (int i = count - 1; i >= 0; i--) //print out string backwards, skip the newline at the end
        printf("%s", &incoming_chars[i]);

    printf("\n");
    return 0;
}