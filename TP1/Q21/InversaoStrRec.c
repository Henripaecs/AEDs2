#include <stdio.h>
#include <string.h>

void inverterString(char *str, int esquerda, int direita){
    if (esquerda >= direita){
        return;
    }

    char aux = str[esquerda];
    str[esquerda] = str[direita];
    str[direita] = aux;

    inverterString(str, esquerda + 1, direita - 1);
    
}

int main() {
    char palavra[1000];

    while (1) {
        fgets(palavra, sizeof(palavra), stdin);
        palavra[strcspn(palavra, "\n")] = 0;

        if (strcmp(palavra, "FIM") == 0){
            break;
        }

        inverterString(palavra, 0, strlen(palavra) - 1);
        printf("%s\n", palavra);
    }

    return 0;
}
