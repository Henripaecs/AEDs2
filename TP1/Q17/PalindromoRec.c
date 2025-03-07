#include <stdio.h>
#include <string.h>
#include <stdbool.h>

bool ehPalindromo(char *str, int esquerda, int direita) {    
    if (esquerda >= direita) {
        return true;
    }
    if (str[esquerda] != str[direita]) {
        return false;
    }
    return ehPalindromo(str, esquerda + 1, direita - 1);
}

int main() {
    char str[1000];

    while (true) {
        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = 0;

        if (strcmp(str, "FIM") == 0) {
            break;
        }

        if (ehPalindromo(str, 0, strlen(str) - 1)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    return 0;
}
