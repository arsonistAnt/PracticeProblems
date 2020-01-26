import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

// Test Algorithms here.
fun main(args: Array<String>) {
}

/**
 * Category: String
 *
 * Two Characters
 * HR: https://www.hackerrank.com/challenges/two-characters/problem
 *
 * Runtime O(n^2)
 */
fun twoCharacters(s: String): Int {
    val queue = ArrayDeque<String>().apply { add(s) }
    val replacedStrings = mutableSetOf<String>()
    var maxAlternateCount = 0

    // Generate all possible replaced strings.
    while (queue.isNotEmpty()) {
        val currString = queue.pop()

        // Check if the string length is greater than 2 and does not contain any duplicates.
        if (currString.length > 2 && (currString[0] != currString[1]))
            replacedStrings.add(currString)

        // Remove one character from the current string and add to the queue.
        for (i in 0 until currString.length) {
            queue.add(currString.replace("${currString[i]}", ""))
        }
    }

    // Check whether the replaced strings in the Set contains a alternating string and keep record of the maximum count.
    for (str in replacedStrings) {
        if (isAlternating(str) && (str.length > maxAlternateCount))
            maxAlternateCount = str.length
    }

    return maxAlternateCount
}

// O(m) m => number of alternating characters.
fun isAlternating(s: String): Boolean {
    var isAlternating = true
    var count = 0
    val a = s[0]
    val b = s[1]

    // If the string contains just duplicates return false.
    if (a == b) return false

    while (isAlternating && (count <= s.length - 2)) {
        if (a != s[count] || b != s[count + 1])
            isAlternating = false
        count += 2
    }

    return isAlternating
}

/**
 * Two Characters with 2D matrix implementation and O(n) runtime.
 * https://www.hackerrank.com/challenges/two-characters/forum/comments/193462
 *
 * A 26 x 26 matrix is used to keep track of all alternating characters (26 because of all letters in alphabet). While parsing through each character
 * we can check if the column and row position of the character (i.e. matrix[row][char] and matrix[char][column]) is empty or a different character.
 *
 * If that's the case then an alternating string exists and we increment a parallel matrix (we'll call it pMatrix[row][char] & pMatrix[char][column]) by one.
 *
 * If there exists the same character then a repetition exists in the matrix position and we initialize the position with -1 to mark it off completely.
 */
fun twoCharactersEfficient(s: String) : Int {
    // Initialize the character matrix with a null character.
    val cMatrix = Array(26) { Array(26) { '\u0000' } }
    // Initialize the integer matrix with all zeros.
    val pMatrix = Array(26) { Array(26) { 0 } }
    var maxUpdates = 0

    // Return length zero since an alternating string may only exists with two or more characters.
    if(s.length <= 1) return 0
    
    for(ch in s){
        for(i in 0 until cMatrix.count()){
            // Negative offset by 97 to start since ascii for 'a' is 97.
            val j = ch.toInt() - 97

            // Check if position in matrix is different than the current character and then increment the parallel matrix.
            if(cMatrix[i][j] != ch && pMatrix[i][j] != -1){
                cMatrix[i][j] = ch
                cMatrix[j][i] = ch
                pMatrix[i][j]++

                // Check if j == i or if the character is at its own matrix position do not increment.
                if(j != i)
                    pMatrix[j][i]++
            }
            // Check if a repetition exists and mark off the matrix position.
            else if(cMatrix[i][j] == ch){
                pMatrix[i][j] = -1
                pMatrix[j][i] = -1
            }
        }
    }

    // Iterate through the parallel matrix to get the maximum length value.
    for(i in 0 until cMatrix.count()){
        for(j in 0 until cMatrix.count()){
            if(pMatrix[i][j] > maxUpdates)
                maxUpdates = pMatrix[i][j]
        }
    }
    return maxUpdates
}


/**
 * Category: String
 *
 * Strong Password
 * HR: https://www.hackerrank.com/challenges/strong-password/problem
 */
// Complete the minimumNumber function below.
fun minimumNumber(password: String): Int {
    // Used to check for special characters
    val special_characters = "!@#$%^&*()-+"

    // Boolean to check if the password has these criteria.
    var hasDigit = false
    var hasSpecial = false
    var hasLower = false
    var hasUpper = false

    val maxCriteria = 4
    var criteriaPoint = 0
    for (ch in password) {
        if (!hasLower && ch.isLowerCase()) {
            hasLower = true
            criteriaPoint++
        } else if (!hasUpper && ch.isUpperCase()) {
            hasUpper = true
            criteriaPoint++
        } else if (!hasDigit && ch.isDigit()) {
            hasDigit = true
            criteriaPoint++
        } else if (!hasSpecial && special_characters.contains(ch)) {
            hasSpecial = true
            criteriaPoint++
        }
    }

    // Calculate the amount of minimum characters needed for a strong password.
    var criteriaMissing = maxCriteria - criteriaPoint

    // Check how many characters are needed to fill the greater than 6 length minimum requirement.
    if ((password.length + criteriaMissing) < 6) {
        criteriaMissing += 6 - (password.length + criteriaMissing)
    }

    // Return the minimum number of characters to make the password strong
    return criteriaMissing
}

/**
 * Category: String
 *
 * Super Reduced String
 * HR: https://www.hackerrank.com/challenges/reduced-string/problem
 */
fun superReducedString(s: String): String {
    var loopAgain = false
    var reducedString = s
    do {
        // Initially set the variable back to false.
        loopAgain = false

        var i = 0
        // Loop to remove any adjacent duplicates.
        while (i < reducedString.length - 1) {
            if (reducedString[i] == reducedString[i + 1]) {
                reducedString = reducedString.substring(0, i) + reducedString.substring(i + 2)
                loopAgain = true
            }
            i++
        }

    } while (loopAgain)

    // If the reduced string is empty then attach the "Empty String".
    if (reducedString.isEmpty())
        reducedString = "Empty String"

    return reducedString
}

/**
 * Super Reduced String with stack implementation O(n) runtime.
 * https://www.hackerrank.com/challenges/reduced-string/forum/comments/268627
 */
fun superReducedStringStack(s: String): String {
    val stack = ArrayList<Char>()
    var top = 0

    if (s.length > 1) {
        stack.add(s[0])
        for (i in 1 until s.length) {
            // Check if the current elem is equal to the element on top of the stack.
            if (top >= 0 && stack[top] == s[i]) {
                // Pop the element on the top of the stack if its equivalent to the current element.
                stack.removeAt(top)
                top--
            }
            // Add the elem to the stack if its not equal to the current stack.
            else {
                top++
                stack.add(s[i])
            }
        }
        // The current stack now has the super reduced string.
        var newString = "Empty String"
        if (stack.size > 0)
            newString = stack.joinToString(separator = "")
        return newString
    } else {
        return s
    }
}

/**
 * Remove duplicate from strings.
 */
fun removeDuplicateStrings(s: String): String {
    var newString = ""
    for (i in 0 until s.length - 1) {
        if (s[i + 1] != s[i])
            newString += s[i]
    }
    // Append the last remaining string.
    newString += s[s.length - 1]
    return newString
}

/**
 * Implement MergeSort
 */
fun mergeSort(arr: ArrayList<Int>, start: Int, end: Int): ArrayList<Int> {
    if (end <= start)
        return arrayListOf(arr[start])

    val middle = (start + end) / 2
    val left = mergeSort(arr, start, middle)
    val right = mergeSort(arr, middle + 1, end)

    return mergeSorted(left, right)
}

fun mergeSorted(a: ArrayList<Int>, b: ArrayList<Int>): ArrayList<Int> {
    val newArray = ArrayList<Int>()
    var i = 0
    var j = 0

    while (i < a.size && j < b.size) {
        if (a[i] < b[j]) {
            newArray.add(a[i])
            i++
        } else {
            newArray.add(b[j])
            j++
        }
    }

    while (i < a.size) {
        newArray.add(a[i])
        i++
    }
    while (j < b.size) {
        newArray.add(b[j])
        j++
    }

    return newArray
}

/**
 * Implementation of Quick sort.
 */
fun quickSort(arr: Array<Int>, start: Int, end: Int) {
    if (start < end) {

        val pivot = partitionArray(arr, start, end)
        quickSort(arr, start, pivot - 1)
        quickSort(arr, pivot + 1, end)
    }
}

fun partitionArray(arr: Array<Int>, start: Int, end: Int): Int {
    val pivot = arr[end]
    var rightIndex = start - 1

    for (i in start until end) {
        if (arr[i] < pivot) {
            rightIndex++
            val temp = arr[rightIndex]
            arr[rightIndex] = arr[i]
            arr[i] = temp
        }
    }
    val temp = arr[rightIndex + 1]
    arr[rightIndex + 1] = pivot
    arr[end] = temp

    return rightIndex + 1
}

/**
 * Count the number of negative's present in the 2D matrix that's been sorted in non-increasing order.
 */
fun count2DArrayNegativesProblem() {
    val arr = arrayOf(
        arrayOf(-3, -2, -1, 1),
        arrayOf(1, 2, 3, 4),
        arrayOf(4, 5, 7, 8)
    )
    val numOfNeg = countNegatives2D(arr, 3, 4)
    print(numOfNeg)
}


// Count the number of negatives for each array that's already sorted.
fun countNegatives2D(arr: Array<Array<Int>>, row: Int, column: Int): Int {
    var count = 0

    // step through n times where n => row number.
    for (i in 0 until row) {
        // step through log(m) times where m => number of negatives present in each nested array.
        count += countRowNegatives(arr[i], 0, column - 1)
    }

    // total runtime will be n * log(m)
    return count
}

fun countRowNegatives(arr: Array<Int>, start: Int, end: Int): Int {
    var count = 0
    var startIndex = 0
    var endIndex = arr.size

    // Stop when the start index is lower than or equal to the end index => We've reached one element.
    while (startIndex < endIndex) {
        val middle = (startIndex + endIndex) / 2
        if (arr[middle] < 0) {
            count += (middle - startIndex) + 1
            startIndex = middle + 1
        } else
            endIndex = middle - 1
    }
    return count
}