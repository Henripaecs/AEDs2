#include <stdio.h>
#include <string.h>
#include <stdbool.h>

bool isPalindromo(char str[], int left, int right) {
    if (left >= right) {
        return true;
    }
    if (str[left] != str[right]) {
        return false;
    }

    if (tolower(str[left]) != tolower(str[right])) {
        return false;
    }
    return isPalindromo(str, left + 1, right - 1);
}

int main() {
    char str[100];    
    
    while (1) {
        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = 0;
        
        if (strcmp(str, "FIM") == 0) {
            break;
        }
        
        if (isPalindromo(str, 0, strlen(str) - 1)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }      
    return 0;
}
