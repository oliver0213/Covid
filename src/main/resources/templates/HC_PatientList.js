$(() => {

    const renderTb = (data) => {
        const html = data.map(({user ,testCenter, email, id, image}) => (`<tr>
                            <td>${user.fullname}</td>
                            <td>${testCenter}</td>
                            <td>${user.email}</td>
                            <td>${image ? ` <img class="img-report" src="data:image/png;base64, ${image}"  />` : ''}</td>

                            <td><input type="radio" data-id="${id}" name="ops"/></td>
                         </tr>`)).join();
        $('#tb_id').html(html)
    }

    const initTb = () => doGet(['/hc/patients'], (data) => {
        renderTb(data);
    })


    initTb();

    $(document).on('click', '#confirm_id', () => {

        console.info('confirm')
        const $center = $('#tb_id input[type=radio]:checked')
        if ($center.length !== 0) {
            const id = $center.attr('data-id');
            window.location.href = `/hc/patient/update/${id}`
        }
        else{
            alert("Please select a Patient")
        }

    })

    $(document).on('click', '#searchBtn', () => {
        const keyword = $('#searchInput').val()
        doGet(['/hc/patients', {keyword}], (data) => {
            renderTb(data)
        })
    })
})
