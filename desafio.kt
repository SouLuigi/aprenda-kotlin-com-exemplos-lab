enum class Nivel { BASICO, INTERMEDIARIO, AVANCADO }

data class Usuario(val nome: String) {
    val minhasFormacao = mutableListOf<Formacao>()

    fun adicinarFormacao(formacao: Formacao) {
        minhasFormacao.add(formacao)
    }

    fun listaFormacoes() {
        println("Formações de $nome")
        if (minhasFormacao.isEmpty()) {
            println("Nenhuma formação adicionada")
        } else {
            minhasFormacao.forEach {
                println("- ${it.nome} (${it.conteudos.size} cursos)")
            }
        }
    }
}

data class ConteudoEducacional(val nome: String, val duracao: Int)

data class Formacao(val nome: String, val nivel: Nivel, var conteudos: MutableList<ConteudoEducacional> )

class SistemaFormacao {
    private val listaUsuarios = mutableListOf<Usuario>()
    private var usuarioAtual: Usuario? = null
    private val conteudosDisponiveis = listOf(
        ConteudoEducacional("Github", 60),
        ConteudoEducacional("Kotlin", 120),
        ConteudoEducacional("React", 100),
        ConteudoEducacional("Programação Orientado a objetos", 200),
        ConteudoEducacional("Next", 60)
    )
    private val formacoesDisponivel = listOf(
        Formacao(
            "Formação Kotlin", Nivel.AVANCADO, mutableListOf(
                conteudosDisponiveis[1],
                conteudosDisponiveis[2],
                conteudosDisponiveis[4],
            )
        ),
        Formacao(
            "Formação React", Nivel.INTERMEDIARIO, mutableListOf(
                conteudosDisponiveis[2],
                conteudosDisponiveis[3],
            )
        )
    )

    fun iniciar() {
        println("  *---- Bem Vindo ----*")

        while (true) {
            if (usuarioAtual == null) {
                println("Você precisa se cadastrar primeiro")
                cadastrarUsuario()
                continue
            }
            println("\nMenu Principal (Usuário: ${usuarioAtual!!.nome})")
            println("1 - Minhas Formações")
            println("2 - Adicionar Formação")
            println("3 - Trocar de Usuário")
            println("4 - Sair")
            print("Escolha uma opção: ")

            when (readLine()?.toIntOrNull()) {
                1 -> usuarioAtual?.listaFormacoes()
                2 -> adicionarFormacao()
                3 -> cadastrarUsuario()
                4 -> {
                    println("Saindo do sistema...")
                    return
                }
                else -> println("Opção inválida! Tente novamente.")
            }
        }
    }

    private fun cadastrarUsuario() {
        println("Digite o nome de usuário:")
        val nomeUsuario = readLine()

        if (!nomeUsuario.isNullOrBlank()) {
            val usuario = Usuario(nomeUsuario)
            listaUsuarios.add(usuario)
            usuarioAtual = usuario
            println("Usuário ${usuario.nome} cadastrado com sucesso!")
        } else {
            println("Nome inválido! Tente novamente.")
            cadastrarUsuario()
        }
    }

    private fun adicionarFormacao() {
        println("\nFormações disponíveis:")
        formacoesDisponivel.forEachIndexed { index, formacao ->
            println("${index + 1} - ${formacao.nome} (${formacao.conteudos.size} cursos) Nivel: ${formacao.nivel}")
        }
        print("Escolha uma formação para adicionar: ")

        val escolha = readLine()?.toIntOrNull()
        if (escolha != null && escolha > 0 && escolha <= formacoesDisponivel.size) {
            val formacaoEscolhida = formacoesDisponivel[escolha - 1]
            usuarioAtual?.adicinarFormacao(formacaoEscolhida)
            println("Formação '${formacaoEscolhida.nome}' adicionada com sucesso!")
        } else {
            println("Opção inválida!")
        }
    }
}
fun main() {
    SistemaFormacao().iniciar()
}