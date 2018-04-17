import sys
import io
import time
import requests
import json

USER_AGENT = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36'
LOGIN_URL = 'https://booking.carleton.ca/Portal/Portal/Services/Login.php'
LOGIN_TEST = 'https://booking.carleton.ca/Portal/index.php?p=FindRoomSS&r=1'
LOGIN_PAGE = 'https://booking.carleton.ca/Portal/index.php'
FINDROOM_URL = 'https://booking.carleton.ca/Portal/Portal/Services/SelfServiceFindRoomList.php'
BOOKING_URL = 'https://booking.carleton.ca/Portal/index.php?p=ConfirmBooking'
CREATE_BOOKING_URL = 'https://booking.carleton.ca/Portal/Portal/Services/CreateBookingRequest.php'
search_data = {
    'cboRequestType': 'b3bbdbb9-9a28-4cb9-9ce4-ff38787be37b',
    'startDate': 1524441600,
    'isSelfService': 1,
    'startTime': 1080,
    'duration': 120,
    'endTime': 1200,
    'originalRequestID': '00000000-0000-0000-0000-000000000000'
}

booking_page_param = {
    'p': 'FindRoomSS',
    'r': 1
}

header = {
    'User-Agent': USER_AGENT,
    'Host;': 'booking.carleton.ca',
    'Origin': 'https://booking.carleton.ca',
    'Referer': 'https://booking.carleton.ca/Portal/index.php'
}


booking_confirm_data = {
'selfService': 1,
'txtRequestDisclaimer': 'Select OK to submit this request.',
'txtRoomConfigId': 'e34ac76a-2eec-4d30-923c-a7624ea0c99a', # NEED REPLACED
'txtOriginalRequestId': '00000000-0000-0000-0000-000000000000',
'cboRequestType': 'b684b44a-00ee-43b1-aae1-567f979816df', #ROOM CALLBACK ID
'txtNumberOfAttendees': 0,
'dpStartDate_stamp': 1524441600, #NEED REPLACED TIME
'cboStartTime': 1080, #NEED REPLACED -> room start time,Starttime * 60 = this, e.g. starttime is 3p.m,so it's 15*60 = 900
'cboDuration': 120, # NEED REPLACED -> booking duration,default 120 is fine for 2 hours
'txtNbOfPeople': 0,
'txtMinArea': 0,
'txtRoomId': 'e34ac76a-2eec-4d30-923c-a7624ea0c99a', #NEED REPLACED  ROOM ROW ID
'cboRoomConfiguration': 'e34ac76a-2eec-4d30-923c-a7624ea0c99a', #NEED Replaced, ROOM ROW ID
'btnConfirm': 'Confirm'
}

def loginAttempt(username,password):
    session = requests.Session()
    res_login_page = session.get(LOGIN_PAGE)  # go to login page -> get cookie
    data = {'txtUsername': username, 'txtPassword': password}
    cookie = session.cookies
    res = requests.post(LOGIN_URL, data=data, cookies=cookie)  # send login request with cookie
    time.sleep(2)
    res_login_page = session.get(LOGIN_PAGE, cookies=cookie)  # goto login page again
    # good above
    res = session.post(FINDROOM_URL, cookies=cookie, data=search_data)  # get room information
    # good above
    print(res.text)
    json_str = res.text
    json_bgein_index = json_str.find("data-listData=")
    json_str = json_str[json_bgein_index + len("data-listData=") + 1:]
    json_end_index = json_str.find("></div>")
    json_str = json_str[:json_end_index - 1]
    print(json_str)
    json_obj = json.load(io.StringIO(json_str))
    print(json_obj["RowData"])
    room_list = json_obj["RowData"]
    room_json = None
    room_id = None
    for item in room_list:
        if (item["rowData"][2] == '324D'):
            room_json = item
            room_id = item["rowID"]
            print(item)
            break
    room_type_id = room_json["callbackArgument"][0]
    print(room_id)
    # good above
    res = requests.post(CREATE_BOOKING_URL,data=booking_confirm_data,cookies=cookie)



loginAttempt()
