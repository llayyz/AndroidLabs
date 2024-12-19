const findDublicates = (arr1, arr2) => {

    let hasDublicates = false

    for (let i = 0; i < arr1.length; i++) {
        const elem = arr2.indexOf(arr1[i])

        if (elem >= 0) {
            hasDublicates = true
            break
        }
    }

    return hasDublicates
}

const arr_1_1 = ['a', 'b', 'c', 'x']
const arr_1_2 = ['z', 'y', 'i']

console.log(findDublicates(arr_1_1, arr_1_2))

const arr_2_1 = ['a', 'b', 'x', 'c']
const arr_2_2 = ['z', 'y', 'x']

console.log(findDublicates(arr_2_1, arr_2_2))