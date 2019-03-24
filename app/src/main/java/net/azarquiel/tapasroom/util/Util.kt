package net.azarquiel.tapasroom.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.*

/**
 * Autor: Paco Pulido 8/11/2018
 */

class Util {
    companion object {
        private lateinit var context: Context
        private const val nombreDB:String="tapitas.db"

        fun inyecta(context: Context) {
            this.context = context
            if (!File("/data/data/${context.packageName}/databases/${nombreDB}").exists()) {
                Toast.makeText(context,"Copiando PokemonDB....", Toast.LENGTH_LONG).show()
                copiar()
            }
        }
        private fun copiar() {
            creaDirectorio()
            copiar("${nombreDB}")
        }

        private fun creaDirectorio() {
            val file = File("/data/data/${context.packageName}/databases")
            file.mkdir()
        }

        private fun copiar(file: String) {
            val ruta = ("/data/data/${context.packageName}/databases/$file")
            var input: InputStream? = null
            var output: OutputStream? = null
            try {
                input = context.assets.open(file)
                output = FileOutputStream(ruta)
                copyFile(input, output)
                input!!.close()
                output.close()
            } catch (e: IOException) {
                Log.e("Pokemon", "Fallo en la copia del archivo desde el asset", e)
            }
        }

        private fun copyFile(input: InputStream?, output: OutputStream) {
            val buffer = ByteArray(1024)
            var read: Int
            read = input!!.read(buffer)
            while (read != -1) {
                output.write(buffer, 0, read)
                read = input!!.read(buffer)
            }
        }
    }
}