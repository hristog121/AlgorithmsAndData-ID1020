/*The function reverse_input() takes a characters (letters or numbers) from the input until a new line is 
introduced (pressing Enter) and prints them in reverse order,

This is done recursively 
 */

#include <stdio.h>


//Declaration before call. If it is not declared - error: implicit declaration. 
void reverse_input();
int main(){
    reverse_input();
    printf("\n");
    return 0;
}

void reverse_input(void){
    int incoming_char = getchar();

    //Stop the recursion when a new line is introduced (Pressing Enter on the keyboard)
    if (incoming_char == '\n')
    {
        return;
    } else
    {   
        //Call the func again 
        reverse_input();
        printf(&incoming_char);
        return;
    }
    
    
}