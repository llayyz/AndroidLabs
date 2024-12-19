const containsCommonItem = (arr1, arr2) => {
    let map = {}
    for (let i = 0; i < arr1.length; i++) {
        if (!map[arr1[i]]) {
            const item = arr1[i]
            map[item] = true
        }
    }

    for (let j = 0; j < arr1.length; j++) {
        if (map[arr2[j]]) {
            return true
        }
    }

    return false
}


const arr_1_1 = ['a', 'b', 'c', 'x']
const arr_1_2 = ['z', 'y', 'i']

console.log(containsCommonItem(arr_1_1, arr_1_2))

const arr_2_1 = ['a', 'b', 'x', 'c']
const arr_2_2 = ['z', 'y', 'x']

console.log(containsCommonItem(arr_2_1, arr_2_2))