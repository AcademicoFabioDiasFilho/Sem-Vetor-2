import javax.swing.*;

enum CategoriaDeSexo {
    MACHO("Macho"), FEMEA("Fêmea");

    private final String nome;

    CategoriaDeSexo(final String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}

final class Start {
    private Start() {

    }

    public static void main(final String[] args) {
        // Começa o programa
        comecar();
    }

    private static void comecar() {
        long tempoInicial = System.currentTimeMillis();
        long tempoInput;

        /*
         Declaração das variáveis locais.
         menorAltura recebe MAX_VALUE, pois só pode ser comparada com qualquer outro número igual ou menor do que ele
         mesmo.

         mediaDeAlturaDosMachos recebe zero, depois será somada com a altura dos machos e então será divida pelo
         numeroDeMachos.

         maiorAltura recebe MAX_VALUE negativo, pois só pode ser comparada com qualquer outro número igual ou maior do
         que ele mesmo.

         numeroDeMachos e numeroDeFemeas recebem zero, e servirão como contadores de machos e femêas respectivamente.

         mediaDeAlturaDosMachos recebe zero, e será somada com a altura dos machos.
        */
        double menorAltura = Double.MAX_VALUE;
        double mediaDeAlturaDosMachos = 0D;
        double maiorAltura = -Double.MAX_VALUE;
        int numeroDeMachos = 0, numeroDeFemeas = 0;

        for (int i = 0; i < 10; ) {
            /*
             Salvando o que as duas strings desse código tem em comum
             (para não repetir o mesmo código)
             \u00AA é o código do indicador ordinal
             i + 1 porque i começa em zero
            */
            final String ordinal = String.format(" da %d\u00AA pessoa", i + 1);

            // Entrada da altura da pessoa do usuário
            tempoInput = System.currentTimeMillis();
            final String alturaStr = JOptionPane.showInputDialog(null, String.format("Qual é a altura%s (Responda em metros, exemplo de resposta: 1.75 ou 2)?", ordinal), null, JOptionPane.QUESTION_MESSAGE);
            // Acrescenta o tempo que o usuário gastou no input ao tempoInicial
            tempoInicial += System.currentTimeMillis() - tempoInput;

            // Se o usuário selecionou a opção cancelar, o programa é finalizado.
            if (alturaStr == null) {
                return;
            }

            /*
             Tenta converter a entrada da altura da pessoa do usuário de String (texto) para double (número real) e
             guarda na variável local altura.
             Se o usuário errar a entrada, o programa o alertará e a entrada de dados da vez será reiniciada.
            */
            final double altura;

            try {
                altura = Double.parseDouble(alturaStr);
            } catch (final NumberFormatException e) {
                tempoInput = System.currentTimeMillis();
                JOptionPane.showMessageDialog(null, String.format("\"%s\" não é um número reconhecido.", alturaStr));
                tempoInicial += System.currentTimeMillis() - tempoInput;
                continue;
            }

            menorAltura = Math.min(menorAltura, altura);
            maiorAltura = Math.max(maiorAltura, altura);

            /*
             Pergunta ao usuário qual é o sexo da pessoa ordinal, caso o usuário selecione "Macho", o número de machos
             aumentará em um, caso o usuário selecione "Femêa", o número de femêas aumentará em um, caso o usuário
             cancele, a entrada de dados da vez será reiniciada.
            */
            tempoInput = System.currentTimeMillis();
            final Object sexoObj = JOptionPane.showInputDialog(null, String.format("Qual é o sexo%s?", ordinal), null, JOptionPane.QUESTION_MESSAGE, null, new CategoriaDeSexo[]{CategoriaDeSexo.MACHO, CategoriaDeSexo.FEMEA}, CategoriaDeSexo.MACHO);
            tempoInicial += System.currentTimeMillis() - tempoInput;

            if (sexoObj == null) {
                continue;
            }

            final CategoriaDeSexo sexo = (CategoriaDeSexo) sexoObj;

            switch (sexo) {
                case MACHO:
                    mediaDeAlturaDosMachos += altura;
                    numeroDeMachos++;
                    break;
                case FEMEA:
                    numeroDeFemeas++;
                    break;
            }

            i++;
        }

        mediaDeAlturaDosMachos = mediaDeAlturaDosMachos / numeroDeMachos;

        tempoInput = System.currentTimeMillis();
        boolean recomecar = !escreverMensagem(String.format("Grupo\nMaior altura: %.2fm\nMenor altura: %.2fm", maiorAltura, menorAltura));
        tempoInicial += System.currentTimeMillis() - tempoInput;

        if (!recomecar) {
            return;
        }

        if (!Double.isNaN(mediaDeAlturaDosMachos)) {
            tempoInput = System.currentTimeMillis();
            recomecar = !escreverMensagem(String.format("Média de altura dos homens: %.2fm", mediaDeAlturaDosMachos));
            tempoInicial += System.currentTimeMillis() - tempoInput;

            if (recomecar) {
                return;
            }
        } else {
            tempoInput = System.currentTimeMillis();
            recomecar = !escreverMensagem("Média de altura dos homens: Indefinida.");
            tempoInicial += System.currentTimeMillis() - tempoInput;

            if (recomecar) {
                return;
            }
        }

        tempoInput = System.currentTimeMillis();
        recomecar = !escreverMensagem(String.format("Número de mulheres: %d", numeroDeFemeas));
        tempoInicial += System.currentTimeMillis() - tempoInput;

        if (recomecar) {
            System.exit(0);
        }

        System.out.printf("O 1º algoritmo foi executado em %d milissegundos.%n", System.currentTimeMillis() - tempoInicial);
    }

    /*
     Exibe uma mensagem de confirmação na tela, caso o usuário cancele, o programa será executado e o método retornará
     falso, caso o usuário feche a mensagem, o programa será finalizado imediatamente, caso o usuário confirme, o
     método retornará verdadeiro.
    */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean escreverMensagem(final String mensagem) {
        final int escolha = JOptionPane.showConfirmDialog(null, mensagem, null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null);

        switch (escolha) {
            case JOptionPane.CANCEL_OPTION:
                comecar();
                return false;
            case JOptionPane.CLOSED_OPTION:
                System.exit(0);
        }

        return true;
    }
}
