document.addEventListener('DOMContentLoaded', function () {
    const modal = document.querySelector('.modal');
    const modalCloseButton = document.querySelector('.modal-close');
    const listButton = document.querySelector('.list-button');
    const backButton = document.querySelector('.back-button');
    const createOrder = document.querySelector('.create-order-btn');

    if (listButton) {
        listButton.addEventListener('click', function () {
            modal.style.display = 'block';
        });
    }

    if (modalCloseButton) {
        modalCloseButton.addEventListener('click', function () {
            modal.style.display = 'none';
        });
    }

    window.addEventListener('click', function (event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

    backButton.addEventListener('click', () => {
        window.history.back();
    })

    createOrder.addEventListener('click', () => {
        const itemId = document.querySelector('.item-id').value;
        const quantity = document.getElementById('quantity').value;
        const address = document.getElementById('address').value;
        const phoneNumber = document.getElementById('number').value;

        if (!validateQuantity(quantity)) {
            alert("수량이 올바르지 않습니다.");
            return;
        }

        $.ajax({
            url: '/api/orders',
            method: 'post',
            dataType: 'json',
            contentType: 'application/json',
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify({
                itemIdList: [
                    {itemId: itemId, quantity: quantity}
                ],
                address: address,
                phoneNumber: phoneNumber
            }),
            success: function (data) {
                console.log(data);

                $.ajax({
                    url: '/api/payment',
                    method: 'post',
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    xhrFields: {
                        withCredentials: true
                    },
                    success: function (data) {
                        console.log(data);

                        window.location.href = data;
                    }
                })
            },
            error: function (data, status, error) {
                console.log(data);
                console.log(status);
                console.log(error);
                alert('주문서 생성 중 문제가 발생했습니다.\n[status: ' + data.status + ']\n[error: ' + data.responseText + ']');
            }
        })
    })
});

function validateQuantity(quantity) {
    const totalSalesUnit = document.querySelector('.total-sales-unit').value;

    return (1 <= quantity) && (quantity <= parseInt(totalSalesUnit));
}
