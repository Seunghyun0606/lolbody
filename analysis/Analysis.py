import csv
import numpy as np
import pandas as pd
from IPython.display import display

data = pd.read_csv('./csv/200730/diamond/200730_top.csv', encoding='latin1')
print(data)