function get_request(context) {
    if (context && context.request)
        return context.request.rawRequest;
    return {}
}

function get_server_action(request){
    if (request &&
        request.payload && 
        request.payload.data &&
        request.payload.data.server_action){
            return request.payload.data.server_action;
        }
    return {};
}

function get_screen(request){
    if (request &&
        request.payload &&
        request.payload.meta &&
        request.payload.meta.current_app &&
        request.payload.meta.current_app.state){
        return request.payload.meta.current_app.state.screen;
    }
    return "";
}

function get_selected_item(request){
if (request &&
        request.payload &&
        request.payload.meta &&
        request.payload.meta.current_app &&
        request.payload.meta.current_app.state){
        return request.payload.selected_item;
    }
    return null;
}

function get_items(request){
if (request &&
        request.payload &&
        request.payload.meta &&
        request.payload.meta.current_app &&
        request.payload.meta.current_app.state &&
        request.payload.meta.current_app.state.item_selector){
        return request.payload.meta.current_app.state.item_selector.items;
    }
    return null;
}

function get_id_by_selected_item(request){
    var items = get_items(request);
    var selected_item = get_selected_item(request);
    if (selected_item && items) {
        return items[selected_item.index].id
    }
    return null;
}

//

function saveEventData(context, session) {
    var eventData = context && context.request && context.request.data && context.request.data.eventData || {};
    session.eventData = eventData;
}

function loadEventData(session) {
//    var eventData = context && context.session && context.session.eventData;
    var eventData = session && session.eventData || {};
    return eventData;
}

function loadEventDataParam(session, paramName) {
//    var eventData = context && context.session && context.session.eventData;
    var eventData = loadEventData(session);
    var paramValue = eventData && eventData[paramName];
    return paramValue
}
