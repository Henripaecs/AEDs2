#include <stdio.h>

int main() {
    double valor;
    int notas, moedas;
    int centavos;

    scanf("%lf", &valor);

    int reais = (int) valor;
    centavos = (int)((valor - reais) * 100 + 0.5);

    printf("NOTAS:\n");

    notas = reais / 100;
    printf("%d nota(s) de R$ 100.00\n", notas);
    reais %= 100;

    notas = reais / 50;
    printf("%d nota(s) de R$ 50.00\n", notas);
    reais %= 50;

    notas = reais / 20;
    printf("%d nota(s) de R$ 20.00\n", notas);
    reais %= 20;

    notas = reais / 10;
    printf("%d nota(s) de R$ 10.00\n", notas);
    reais %= 10;

    notas = reais / 5;
    printf("%d nota(s) de R$ 5.00\n", notas);
    reais %= 5;

    notas = reais / 2;
    printf("%d nota(s) de R$ 2.00\n", notas);
    reais %= 2;

    printf("MOEDAS:\n");

    printf("%d moeda(s) de R$ 1.00\n", reais);

    moedas = centavos / 50;
    printf("%d moeda(s) de R$ 0.50\n", moedas);
    centavos %= 50;

    moedas = centavos / 25;
    printf("%d moeda(s) de R$ 0.25\n", moedas);
    centavos %= 25;

    moedas = centavos / 10;
    printf("%d moeda(s) de R$ 0.10\n", moedas);
    centavos %= 10;

    moedas = centavos / 5;
    printf("%d moeda(s) de R$ 0.05\n", moedas);
    centavos %= 5;

    moedas = centavos / 1;
    printf("%d moeda(s) de R$ 0.01\n", moedas);

    return 0;
}
