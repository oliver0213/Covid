$(() => {
    const initTb = () => doGet(['/hc/test-centers'], (data) => {
        const html = data.map(({name, address, zipCode, centerId}) => (`<tr>
                            <td>${name}</td>
                            <td>${address}</td>
                            <td>${zipCode}</td>
                            <td><input type="radio" name="ops" data-id="${centerId}"/></td>
                         </tr>`)).join();
        $('#tb_id').html(html)
    })

    initTb();

    $('.ops').on('click', '#confirm_id', () => {

        const isSelected = $('input[name=ops]:checked').val();
        if (isSelected == null){
        alert("Please select a Test Center")
        }
        else {


            const $center = $('#tb_id input[type=radio]:checked')
            if ($center.length !== 0) {
                const id = $center.attr('data-id')
                window.location.href = `/hc/appointment?id=${id}`
            }
        }
    })
})




