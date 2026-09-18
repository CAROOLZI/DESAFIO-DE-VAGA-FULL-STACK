package br.com.iniflex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        // 3.1 – Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 13), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        // 3.2 – Remover o funcionário "João"
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // Formatadores para os requisitos visuais
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat formatadorMoeda = NumberFormat.getInstance(new Locale("pt", "BR"));
        formatadorMoeda.setMinimumFractionDigits(2);

        // 3.3 – Imprimir todos os funcionários com formatação
        System.out.println("--- 3.3 Todos os Funcionários ---");
        funcionarios.forEach(f -> {
            System.out.println("Nome: " + f.getNome() +
                    " | Data de Nasc.: " + f.getDataNascimento().format(formatadorData) +
                    " | Salário: R$ " + formatadorMoeda.format(f.getSalario()) +
                    " | Função: " + f.getFuncao());
        });

        // 3.4 – Aumento de 10%
        funcionarios.forEach(f -> {
            BigDecimal aumento = f.getSalario().multiply(new BigDecimal("0.10"));
            f.setSalario(f.getSalario().add(aumento));
        });

        // 3.5 e 3.6 – Agrupar por função e imprimir
        System.out.println("\n--- 3.5 e 3.6 Agrupados por Função ---");
        Map<String, List<Funcionario>> agrupadosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
        
        agrupadosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            lista.forEach(f -> System.out.println(" - " + f.getNome()));
        });

        // 3.8 – Imprimir quem faz aniversário no mês 10 e 12
        System.out.println("\n--- 3.8 Aniversariantes Mês 10 e 12 ---");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println(f.getNome() + " (Nasc: " + f.getDataNascimento().format(formatadorData) + ")"));

        // 3.9 – Funcionário com a maior idade
        System.out.println("\n--- 3.9 Funcionário de Maior Idade ---");
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        int idadeMaisVelho = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idadeMaisVelho + " anos");

        // 3.10 – Ordem alfabética
        System.out.println("\n--- 3.10 Ordem Alfabética ---");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));

        // 3.11 – Total dos salários
        System.out.println("\n--- 3.11 Total dos Salários ---");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total: R$ " + formatadorMoeda.format(totalSalarios));

        // 3.12 – Quantidade de salários mínimos
        System.out.println("\n--- 3.12 Salários Mínimos (Referência R$ 1212,00) ---");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> {
            BigDecimal qtdSalarios = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " ganha " + formatadorMoeda.format(qtdSalarios) + " salários mínimos.");
        });
    }
}