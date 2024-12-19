const containsCommonItem = (arr1, arr2) => {
    return arr1.some(item => arr2.includes(item))
}

const arr_1_1 = ['a', 'b', 'c', 'x']
const arr_1_2 = ['z', 'y', 'i']

console.log(containsCommonItem(arr_1_1, arr_1_2))

const arr_2_1 = ['a', 'b', 'x', 'c']
const arr_2_2 = ['z', 'y', 'x']

console.log(containsCommonItem(arr_2_1, arr_2_2))