$(document).ready(function () {

    // Invoke the corresponding URL to update the contacts section using Ajax
    $('.update-formations').on('click', 'button[data-update-formations-url]', function () {
        let url = $(this).data('update-formations-url');

        // adding the row index, needed when deleting a contact
        let formData = $('form').serializeArray();
        let param = {};
        param["name"] = $(this).attr('name');
        param["value"] = $(this).val();
        formData.push(param);

        // updating the contact section
        $('#tblFormations').load(url, formData);
    });

    // autodismiss alerts
    window.setTimeout(function() {
        $(".alert").fadeTo(500, 0).slideUp(500, function(){
            $(this).remove();
        });
    }, 4000);
    
    // Invoke the corresponding URL to update the contacts section using Ajax
    $('.update-experiences').on('click', 'button[data-update-experiences-url]', function () {
        let url = $(this).data('update-experiences-url');

        // adding the row index, needed when deleting a contact
        let formData = $('form').serializeArray();
        let param = {};
        param["name"] = $(this).attr('name');
        param["value"] = $(this).val();
        formData.push(param);

        // updating the contact section
        $('#tblExperiences').load(url, formData);
    });

    // autodismiss alerts
    window.setTimeout(function() {
        $(".alert").fadeTo(500, 0).slideUp(500, function(){
            $(this).remove();
        });
    }, 4000);
});