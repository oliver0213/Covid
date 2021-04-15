const doAjax = (url, data, successFn = () => {
}, failFn = (msg) => {
    console.info('error', msg)
}, opts = {}) => {
    const {
        type = 'GET',
        contentType = 'application/json;charset=utf-8',
        dataType = 'json',
        processData= true,
    } = opts


    $.ajax({
        type: type,
        url: url,
        contentType: contentType,
        dataType: dataType,
        async: false,
        data: data,
        processData:processData,
        success: function (message) {
            successFn(message)
        },
        error: function (message) {
            failFn(message)
        }
    });
}


const doGet = ([url, data], successFn) => {
    return doAjax(url, data, successFn)
}


const doPost = ([url, data], successFn) => {
    return doAjax(url, data, successFn, () => {
    }, {type: 'post'})
}
const doDelete = ([url, data], successFn) => {
    return doAjax(url, data, successFn, () => {
    }, {type: 'delete'})
}


const doForm = ([url, data], successFn) => {
    return doAjax(url, data, successFn, () => {
    }, {type: 'post', contentType: false,processData:false})
}