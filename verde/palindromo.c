#include <stdio.h>
#include <string.h>
#include <ctype.h>

int isPalindrome(char str[], int i, int j){
        if (i >= j){
            return 1;
        }
        if (str[i] != str[j]){
            return 0;
        }
        return isPalindrome(str, i + 1, j - 1);
}

int main() {
    char str[1000];
    
    do {

        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = 0;

        if (strcmp(str, "FIM") == 0) break;

        if (isPalindrome(str, 0, strlen(str) - 1))
            printf("SIM\n");
        else
            printf("NAO\n");

    } while (1);

    return 0;
}