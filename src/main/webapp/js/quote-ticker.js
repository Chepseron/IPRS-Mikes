$(document).ready(function () {
    createTicker();
});
function createTicker(){
    //set the quotes array
    tickerItems = new Array(
        '"There are no great men, only great challenges that ordinary men are forced by circumstances to meet.\n\
                            Man is only truly great when he acts from his passions  \n\
                            The price of greatness is responsibility \n\
                            Excellence is not a destination; it is a continuous journey that never ends."<br/><hr><b>William F. Halsey</b>',
        '"Man is only truly great when he acts from his passions."<br/><hr><b>Benjamin Disreali</b>',
        '"The price of greatness is responsibility."<br/><hr><b>Winston Churchill</b>',
        '"To achieve great things we must live as if we were never going to die."<br/><hr><b>Marquis de Vauvenargues</b>',
        '"Excellence is not a destination; it is a continuous journey that never ends."<br/><hr><b>Brian Tracy</b>',
        '"Great works are performed, not by strength, but by perseverance."<br/><hr><b>Dr. charles</b>',
        '" kindly submit your appraisal forms before the 28TH of february."<br/><hr><b>Charles K Chesang</b>'
        );        
    i = 0;
    tickerIt();
}
function tickerIt(){
    if( i == tickerItems.length ){
        i = 0;
    }  
    $('#ticker').fadeOut("slow", function(){
        $(this).html(tickerItems[i]).fadeIn("slow");
        i++;
    });

    //repeat - change 5000 - time interval
    setTimeout(tickerIt, 10000);
    
}