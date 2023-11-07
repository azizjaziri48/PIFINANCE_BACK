import talib
import numpy as np
import streamlit as st
import websocket
import json

# Streamlit UI
st.title("Trading Bot Dashboard")

# Trading Strategy Parameters
amount = 1000
core_trade_amount = amount * 0.80
trade_amount = amount * 0.20
core_to_trade = True
core_quantity = 0
portfolio = 0
investment, real_time_portfolio_value, closes, highs, lows, opens = [], [], [], [], [], []
money_end = amount

# Functions for buy and sell
def buy(allocated_money, price):
    global money_end, portfolio
    quantity = allocated_money / price
    money_end -= quantity * price
    portfolio += quantity
    if investment == []:
        investment.append(allocated_money)
    else:
        investment.append(allocated_money)
        investment[-1] += investment[-2]

def sell(allocated_money, price):
    global money_end, portfolio
    quantity = allocated_money / price
    money_end += allocated_money
    portfolio -= quantity
    investment.append(-allocated_money)
    investment[-1] += investment[-2]

# Function for on_close
def on_close(ws, close_status_code, close_msg):
    portfolio_value = portfolio * closes[-1]
    if portfolio_value > 0:
        sell(portfolio_value, price=closes[-1])
    else:
        buy(-portfolio_value, price=closes[-1])
    money_end += investment[-1]
    st.write("All trades settled")

# Function for on_message
def on_message(ws, message):
    global closes, highs, lows, opens, core_to_trade, core_quantity, money_end, portfolio, investment, real_time_portfolio_value
    json_message = json.loads(message)
    cs = json_message['k']
    candle_closed, close, open, low, high = cs['x'], cs['c'], cs['o'], cs['l'], cs['h']

    if candle_closed:
        closes.append(float(close))
        highs.append(float(high))
        lows.append(float(low))
        opens.append(float(open))
        last_price = closes[-1]

        if core_to_trade:
            buy(core_trade_amount, price=last_price)
            st.write(f'Core Investment: We bought ${core_trade_amount} worth of bitcoin')
            core_quantity += core_trade_amount / last_price
            core_to_trade = False

        engulfing = talib.CDLENGULFING(np.array(opens), np.array(highs), np.array(lows), np.array(closes))
        last_eng = engulfing[-1]
        amt = last_eng * trade_amount / 100
        port_value = (portfolio - core_quantity) * last_price
        trade_amt = amt - port_value

        RT_portfolio_value = money_end + portfolio * last_price
        real_time_portfolio_value.append(float(RT_portfolio_value))

        st.write(f'The Last Engulfing Value is "{last_eng}" and recommended exposure is "${trade_amt}"')
        st.write(f'The Real-Time Portfolio Value: ${RT_portfolio_value}')

        if trade_amt >= 0:
            buy(trade_amt, price=last_price)
            st.write(f'We bought ${trade_amt} worth of bitcoin')
        elif trade_amt < 0:
            sell(-trade_amt, price=last_price)
            st.write(f'We sold ${-trade_amt} worth of bitcoin')

# Create a Streamlit button to start the trading bot
if st.button("Start Trading Bot"):
    ws = websocket.WebSocketApp('wss://stream.binance.com:9443/ws/btcusdt@kline_1m', on_message=on_message, on_close=on_close)
    ws.run_forever()
