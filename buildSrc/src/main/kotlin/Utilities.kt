import java.io.File
import java.util.concurrent.TimeUnit


object Utilities {

    @JvmStatic
    fun buildNumber(projectDir: File): Int {
        val baseNumber = 100000
        val proc = ProcessBuilder("git", "rev-list", "--count", "HEAD")
                .directory(projectDir)
                .start().also {
                    it.waitFor(60, TimeUnit.SECONDS)
                }

        if (proc.exitValue() != 0) {
            throw Exception(proc.errorStream.bufferedReader().readText())
        }
        val numberOfCommits = proc.inputStream.bufferedReader().readText().trim().toInt()
        return baseNumber + numberOfCommits
    }



}
