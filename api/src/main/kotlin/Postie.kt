import cli.PostieCommand
import cli.RunCommand
import com.github.ajalt.clikt.core.subcommands

/**
 * Application entry-point.
 */
fun main(args: Array<String>) {
    PostieCommand()
        .subcommands(RunCommand())
        .main(args)
}
