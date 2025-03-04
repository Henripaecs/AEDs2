#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdbool.h>

bool Palindromo(char str[]) {
    int esquerda = 0;
    int direita = strlen(str) - 1;
    
    while (esquerda < direita) {
        while (esquerda < direita && !isalnum(str[esquerda])) esquerda++;
        while (esquerda < direita && !isalnum(str[direita])) direita--;
        
        if (tolower(str[esquerda]) != tolower(str[direita])) {
            return false;
        }
        esquerda++;
        direita--;
    }
    return true;
}

int main() {
    char str[100];    
    while (true){
        fgets(str, sizeof(str), stdin);

        str[strcspn(str, "\n")] = 0;

        if(strcmp(str, "FIM") == 0){
            break;
        }

        if (Palindromo(str)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }        
    }      
    return 0;
}
