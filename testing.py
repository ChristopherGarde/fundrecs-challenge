import requests
import datetime
from datetime import timedelta
import random
import json

# Number of transactions to create in a single run
num_trx = 150
# Whether or not these transactions should be created in a single batch call (alternate is num_trx individual calls)
batch_call = True
# Whether or not an individual get call should be made after creating transactions
do_single_get = True
# Maximum amount for a transaction (can be 0.99 more)
upper_trx_amount_limit = 1000
# Range in days the random dates can be previous to today
trx_date_limit_in_days = 1000
# Whether or not the contents of the database will be printed in json format (same as expected from a get call)
print_sorted_db = False
# Whether or not the created transactions will have their types randomised. (Current types are listed below + defined in java code)
# Will default to CREDIT
randomise_trx_type = False


# ==========================================

trx_type = ["CREDIT", "DEBIT"]

localhost = "http://localhost:8080"
start_date = datetime.datetime.now()
end_date = start_date + timedelta(days=-trx_date_limit_in_days)

single_date = ""
single_type = ""
single_amount = 0

def create_transaction(date, amount, type=trx_type[0]):
    body = [{
        "type": type,
        "date": date,
        "amount": amount
    }]
    headers = {"Content-type": "application/json"}
    r = requests.post(f"{localhost}/transactions", json=body, headers=headers)
    if r.status_code != 201:
        print(r.text)

def create_transaction_batch(num_to_create, random_type):
    body = generate_body_batch(num_to_create, random_type)
    headers = {"Content-type": "application/json"}
    r = requests.post(f"{localhost}/transactions", json=body, headers=headers)
    if r.status_code != 201:
        print(r.text)
    return f"Finished creating {num_to_create} transactions."

def generate_body_batch(num_to_create, random_type):
    body = []
    for x in range(num_to_create):
        date = get_random_date()
        global single_date
        single_date = date
        amount = get_random_amount()
        global single_amount
        single_amount = amount
        type = trx_type[0]
        if random_type:
            type = get_random_type()
        global single_type
        single_type = type
        body.append({
            "type": type,
            "date": date,
            "amount": amount
        })
    return body

def get_transaction(date, type=trx_type[0]):
    r = requests.get(f"{localhost}/transactions/{date}/{type}")
    return r.text

def get_all(return_sorted_string):
    r = requests.get(f"{localhost}/transactions")
    parsed = json.loads(r.text)
    total_records = f"{len(parsed)} total records in DB currently."
    if return_sorted_string:
        return f"{json.dumps(parsed, indent=2)}\n{total_records}"
    else:
        return total_records

def get_random_date():
    random_date = start_date + (end_date - start_date) * random.random()
    return random_date.strftime('%d-%m-%Y')

def get_random_amount(high=upper_trx_amount_limit):
    whole_number = random.randrange(high)
    decimal = round(random.random(), 2)
    amount = whole_number+decimal
    return amount

def get_random_type():
    return trx_type[random.randint(0,len(trx_type))]

def create_x_individual_transactions(number, random_type):
    for x in range(number):
        date = get_random_date()
        global single_date
        single_date = date
        amount = get_random_amount()
        global single_amount
        single_amount = amount
        type = trx_type[0]
        if random_type:
            type = get_random_type()
        global single_type
        single_type = type
        create_transaction(date, amount, type)
    return f"Finished creating {number} transactions.\n"

response = None
if batch_call:
    response = create_transaction_batch(num_trx, random_type=randomise_trx_type)
else:
    response = create_x_individual_transactions(num_trx, random_type=randomise_trx_type)
print(response)
response = get_all(return_sorted_string=print_sorted_db)
print(response)
if do_single_get and single_date and single_type:
    response = get_transaction(single_date, single_type)
    expectation = f"Expecting to get this amount or greater in returned value: {single_amount}"
    print(expectation)
    result = f"Response: {response}"
    print(result)
