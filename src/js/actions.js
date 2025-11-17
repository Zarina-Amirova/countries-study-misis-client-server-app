function continentChoose(params, context) {
    addAction({
        type: "continent_choose",
        params: params,
    }, context);
}

function countryChoose(params, context){
    addAction({
        type: "country_choose",
        params: params,
    }, context);
}

function goBack(params, context){
    addAction({
        type: "back",
        params: params
    }, context);
}

function goBackCard(params, context){
    addAction({
        type: "back_card",
        params: params
    }, context);
}


function modeChoose(params, context){
    addAction({
        type: "mode_choose",
        params: params
    }, context);
}

//function learnTranslate(params, context){
//    addAction({
//        type: "learn_translate",
//        params: params
//    }, context);
//}

function learnFlip(params, context){
    addAction({
        type: "learn_flip",
        params: params
    }, context);
}

function learnNext(params, context){
    addAction({
        type: "learn_next",
        params: params
    }, context);
}

function learnPrev(params, context){
    addAction({
        type: "learn_prev",
        params: params
    }, context);
}
function result(params, context){
    addAction({
        type: "result",
        params: params
    }, context);
}

