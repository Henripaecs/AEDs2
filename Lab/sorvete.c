#include <stdio.h>
#include <stdlib.h>

// Estrutura para armazenar os intervalos
typedef struct {
    int start, end;
} Interval;

// Função de comparação para ordenar os intervalos pelo início (start)
int compare(const void *a, const void *b) {
    return ((Interval*)a)->start - ((Interval*)b)->start;
}

int main() {
    int test_case = 1;
    int P, S; 
    
    while (1) {
        scanf("%d %d", &P, &S);
        
        if (P == 0 && S == 0) {
            break;
        }

        Interval sorveteiros[S];

        for (int i = 0; i < S; i++) {
            scanf("%d %d", &sorveteiros[i].start, &sorveteiros[i].end);
        }

        qsort(sorveteiros, S, sizeof(Interval), compare);

        printf("Teste %d\n", test_case++);
        
        int current_start = sorveteiros[0].start;
        int current_end = sorveteiros[0].end;

        for (int i = 1; i < S; i++) {
            if (sorveteiros[i].start <= current_end) {
                if (sorveteiros[i].end > current_end) {
                    current_end = sorveteiros[i].end;
                }
            } else {
                printf("%d %d\n", current_start, current_end);
                current_start = sorveteiros[i].start;
                current_end = sorveteiros[i].end;
            }
        }

        printf("%d %d\n", current_start, current_end);
    }

    return 0;
}
