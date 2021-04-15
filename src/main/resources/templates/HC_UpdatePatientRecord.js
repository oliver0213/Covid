$(function () {
    let date = new Date($('#dateId').val());
    let uploaded = $('#uploadId').val();
    const now = new Date()
    const dateNow = new Date(`${now.getFullYear()}-${now.getMonth() + 1}-${now.getDate()}`)

    console.info('date <',date,dateNow)
    if (uploaded==='true') {
        console.info('completed')
        $('#completed').attr('checked', true)
    } else if (date < dateNow) {
        $('#pending').attr('checked', true)
    } else {
        $('#scheduled').attr('checked', true)
    }

    $(document).on('click', '#upload', () => {
        const file = $('#file')[0].files[0]
        if (!file) return

        const formData = new FormData()
        formData.append("file", file);
        formData.append("status", $("input[name=status]:checked").val());
        const id = $('#patientId').val()
        doForm([`/hc/patients/${id}`, formData], () => {
            window.location.href = '/hc/patient'
        })
    })
})