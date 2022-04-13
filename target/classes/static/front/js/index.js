$(function () {
    let colors = ['#00a67c', '#d9534f', '#528d05', '#057fe7', '#0cc311', '#d90b9a', '#ff6600', '#af1f82', '#bd6d09', '#e51515']
    let i = 0
    $('.recent-item').each(function (i) {
        $(this).css('background-color', colors[i])
    })
    $('.category-item').each(function () {
        if (i > 9) {
            i = 0
        }
        $(this).css('background-color', colors[i])
        i++
    })
    i = 0
    $('.tag-item').each(function () {
        if (i > 9) {
            i = 0
        }
        $(this).css('background-color', colors[i])
        i++
    })

})