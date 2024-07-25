package hu.bme.aut.android.minesweeper.data.gamedata

import android.content.Context
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import kotlin.random.Random

data class GameTable(private val row:Int, val column:Int, private val mineNum:Int): Serializable {
    val Table : Array<Array<GameField>> = Array(row) { Array(column) { GameField() } }
    var remaining = mineNum
    val Size: Int
        get() = column * row

    init{
        fillTable()
    }

    private fun fillTable(){
        var putMine = mineNum
        while(putMine > 0){
            val pickedRow = Random.nextInt(0,row)
            val pickedColumn = Random.nextInt(0,column)
            if(Table[pickedRow][pickedColumn].value != 9){
                Table[pickedRow][pickedColumn].value = 9
                putMine--
            }
        }
        for(i in 0 until row)
            for(j in 0 until column)
                if(Table[i][j].value != 9)
                    for(k in (i - 1).coerceAtLeast(0) until (i + 2).coerceAtMost(row))
                        for(l in (j - 1).coerceAtLeast(0) until (j + 2).coerceAtMost(column))
                            if(Table[k][l].value == 9)
                                Table[i][j].value++
    }

    suspend fun openEmpty(currentRow: Int, currentColumn: Int){
        val startRow = currentRow - 1
        val startColumn = currentColumn - 1
        val endRow = currentRow + 2
        val endColumn = currentColumn + 2
        for(i in startRow.coerceAtLeast(0) until endRow.coerceAtMost(row)) {
            for (j in startColumn.coerceAtLeast(0) until endColumn.coerceAtMost(column)) {
                if (!Table[i][j].open) {
                    if(Table[i][j].value == 0) {
                        if(!Table[i][j].flagged) {
                            Table[i][j].open = true
                            openEmpty(i, j)
                        }
                    }
                    else {
                        if(!Table[i][j].flagged) {
                            Table[i][j].open = true
                        }
                    }
                }
            }
        }
    }

    suspend fun countScore(): Int{
        var score = 0
        for(i in 0 until row){
            for(j in 0 until column){
                if(Table[i][j].flagged) {
                    if (Table[i][j].value == 9) {
                        score++
                    }
                    else{
                        score--
                    }
                }
            }
        }
        return score
    }

    suspend fun checkAllFlag(): Boolean{
        for(i in 0 until row){
            for(j in 0 until column){
                if(Table[i][j].flagged){
                    if(Table[i][j].value != 9){
                        return false
                    }
                }
            }
        }
        return true
    }

    fun saveTable(filename: String, context: Context){
        val fo: FileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fo)
        os.writeObject(this)
        os.close()
        fo.close()
    }

    fun loadTable(filename: String, context: Context): GameTable{
        try {
            val fi: FileInputStream = context.openFileInput(filename)
            val os = ObjectInputStream(fi)
            val loadedTable = os.readObject() as GameTable
            os.close()
            fi.close()
            return loadedTable
        }catch (e: Exception){
            return GameTable(9,9,10)
        }
    }
}