#include <stdio.h>
#include <ctype.h> 
#include <string.h>

int main() {
    char texto[100];
    int contador;

    while (1) {
        fgets(texto, sizeof(texto), stdin);

        texto[strcspn(texto, "\n")] = '\0';

        if (strcmp(texto, "FIM") == 0){
            break;
        }

        contador = 0; 

        for (int i = 0; texto[i] != '\0'; i++) {
            if (isupper(texto[i])) {
                contador++;
            }
        }

        printf("%d\n", contador);
    }

    return 0;
}
