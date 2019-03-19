
# coding: utf-8

# Import some important libraries

# In[68]:


import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
get_ipython().run_line_magic('matplotlib', 'inline')


import warnings
warnings.filterwarnings('ignore')


# Let's see what the data looks like

# In[69]:


df = pd.read_csv('/home/stephen/Classes/Data Mining/Project 1/risk_factors_cervical_cancer.csv')
df.head(3)


# Get rid of the question marks and place 0's, since we see there are several
# question marks in rows where no STD has been diagnosed

# In[70]:


df = df.drop(['Dx'],axis=1)

df['STDs: Time since first diagnosis'].replace('?', 0, inplace=True)
df['STDs: Time since last diagnosis'].replace('?', 0, inplace=True)
df['STDs: Time since first diagnosis'] = pd.to_numeric(df['STDs: Time since first diagnosis'])
df['STDs: Time since last diagnosis'] = pd.to_numeric(df['STDs: Time since last diagnosis'])


# In[71]:


df.head(10)


# Combine all targets into one column, since there is no specification
# which one we should rely on. The data source says they're all targets

# In[72]:


df['target'] = np.where((df['Hinselmann'] == 1) | (df['Schiller'] == 1) | (df['Citology'] == 1) | (df['Biopsy'] == 1), 1, 0)


# In[73]:


df.head(10)


# Drop the target columns now that they've been combined

# In[74]:


df = df.drop(['Hinselmann', 'Schiller', 'Citology', 'Biopsy'], axis=1)


# Let's see what data is like in each column

# In[75]:


df.info(verbose=True)


# Lets convert the question marks to null values, since the question marks
# that could be interpreted as 0 have been filled in 

# In[76]:


df.replace('?', np.nan, inplace=True)
df.isna().sum()


# All the STD columns coincidentally have the same amount of nulls. Given that,
# if we drop them from one column they should all drop. If they don't,
# dropping the nulls from all the STD columns might delete too much data.

# In[77]:


df = df[pd.notnull(df['STDs'])]
df.isna().sum()


# Since there isn't that many null values remaining, we can drop the rest
# and convert all values to numerical ones.

# In[78]:


df = df.dropna()
df = df.apply(pd.to_numeric)
df.info()


# Lets visualize bindary data columns as pie charts

# In[79]:


Smokes =len(df[df['Smokes'] == 1])
Not = len(df[df['Smokes']== 0])

plt.figure(figsize=(8,6))

labels = 'Smokes','Does Not'
sizes = [Smokes, Not]
colors = ['lavender', 'turquoise']

plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

HC =len(df[df['Hormonal Contraceptives'] == 1])
No = len(df[df['Hormonal Contraceptives']== 0])

plt.figure(figsize=(8,6))

labels = 'Hormonal Contraceptives','None'
sizes = [HC, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

IUD =len(df[df['IUD'] == 1])
No = len(df[df['IUD']== 0])

plt.figure(figsize=(8,6))

labels = 'IUD','No'
sizes = [IUD, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

STD =len(df[df['STDs'] == 1])
No = len(df[df['STDs']== 0])

plt.figure(figsize=(8,6))

labels = 'STD','No'
sizes = [STD, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:condylomatosis'] == 1])
No = len(df[df['STDs:condylomatosis']== 0])

plt.figure(figsize=(8,6))

labels = 'condylomatosis','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:condylomatosis'] == 1])
No = len(df[df['STDs:condylomatosis']== 0])

plt.figure(figsize=(8,6))

labels = 'condylomatosis','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:cervical condylomatosis'] == 1])
No = len(df[df['STDs:cervical condylomatosis']== 0])

plt.figure(figsize=(8,6))

labels = 'cervical condylomatosis','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:vaginal condylomatosis'] == 1])
No = len(df[df['STDs:vaginal condylomatosis']== 0])

plt.figure(figsize=(8,6))

labels = 'vaginal condylomatosis','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:vulvo-perineal condylomatosis'] == 1])
No = len(df[df['STDs:vulvo-perineal condylomatosis']== 0])

plt.figure(figsize=(8,6))

labels = 'vulvo-perineal condylomatosis','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:syphilis'] == 1])
No = len(df[df['STDs:syphilis']== 0])

plt.figure(figsize=(8,6))

labels = 'Syphilis','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:pelvic inflammatory disease'] == 1])
No = len(df[df['STDs:pelvic inflammatory disease']== 0])

plt.figure(figsize=(8,6))

labels = 'Pelvic inflammatory disease','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:genital herpes'] == 1])
No = len(df[df['STDs:genital herpes']== 0])

plt.figure(figsize=(8,6))

labels = 'genital herpes','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:molluscum contagiosum'] == 1])
No = len(df[df['STDs:molluscum contagiosum']== 0])

plt.figure(figsize=(8,6))

labels = 'molluscum contagiosum','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:AIDS'] == 1])
No = len(df[df['STDs:AIDS']== 0])

plt.figure(figsize=(8,6))

labels = 'AIDS','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:HIV'] == 1])
No = len(df[df['STDs:HIV']== 0])

plt.figure(figsize=(8,6))

labels = 'HIV','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:Hepatitis B'] == 1])
No = len(df[df['STDs:Hepatitis B']== 0])

plt.figure(figsize=(8,6))

labels = 'Hepatitis B','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['STDs:HPV'] == 1])
No = len(df[df['STDs:HPV']== 0])

plt.figure(figsize=(8,6))

labels = 'HPV','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Cancer =len(df[df['Dx:Cancer'] == 1])
No = len(df[df['Dx:Cancer']== 0])

plt.figure(figsize=(8,6))

labels = 'Cancer','No'
sizes = [Cancer, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

CIN =len(df[df['Dx:CIN'] == 1])
No = len(df[df['Dx:CIN']== 0])

plt.figure(figsize=(8,6))

labels = 'CIN','No'
sizes = [CIN, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

HPV =len(df[df['Dx:HPV'] == 1])
No = len(df[df['Dx:HPV']== 0])

plt.figure(figsize=(8,6))

labels = 'HPV','No'
sizes = [HPV, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()

Target =len(df[df['target'] == 1])
No = len(df[df['target']== 0])

plt.figure(figsize=(8,6))

labels = 'Cervical Cancer','No'
sizes = [Target, No]
colors = ['lavender', 'turquoise']
 
plt.pie(sizes, labels=labels, colors=colors,
autopct='%1.1f%%', shadow=True, startangle=90)
 
plt.axis('equal')
plt.show()


# Since the AIDS column only has one value, we can drop it.

# In[80]:


df = df.drop('STDs:AIDS', axis = 1)


# Let's visualize the continuous value columns

# In[81]:


fig, (ax1,ax2,ax3,ax4,ax5,ax6,ax7,ax8,ax9,ax10,ax11) = plt.subplots(11,1,figsize=(20,40))
sns.countplot(x='Age', data=df, ax=ax1)
sns.countplot(x='Number of sexual partners', data=df, ax=ax2)
sns.countplot(x='First sexual intercourse', data=df, ax=ax3)
sns.countplot(x='Num of pregnancies', data=df, ax=ax4)
sns.countplot(x='Smokes (years)', data=df, ax=ax5)
sns.countplot(x='Smokes (packs/year)', data=df, ax=ax6)
sns.countplot(x='Hormonal Contraceptives (years)', data=df, ax=ax7)
sns.countplot(x='IUD (years)', data=df, ax=ax8)
sns.countplot(x='STDs (number)', data=df, ax=ax9)
sns.countplot(x='STDs: Number of diagnosis', data=df, ax=ax10)
sns.countplot(x='STDs: Time since last diagnosis', data=df, ax=ax11)


# Now that we've seen the data, lets import the machine learning
# libraries and split the data into training and testing

# In[82]:


from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report,confusion_matrix
from sklearn.linear_model import LogisticRegression
from sklearn.svm import SVC, LinearSVC
from sklearn.ensemble import RandomForestClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import GradientBoostingClassifier

X = df.drop(['target'], axis=1)
y = df['target']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.1, random_state=12)


# Lets run a series of machine learning algorithms and
# see which one works best

# In[83]:


#1) Logistic Regression
logreg = LogisticRegression()
# Train the model using the training sets and check score
logreg.fit(X_train, y_train)
#Predict Output
log_predicted= logreg.predict(X_test)

logreg_score = round(logreg.score(X_train, y_train) * 100, 2)
logreg_score_test = round(logreg.score(X_test, y_test) * 100, 2)
#Equation coefficient and Intercept
print('Logistic Regression Training Score: \n', logreg_score)
print('Logistic Regression Test Score: \n', logreg_score_test)
print('Accuracy: \n', accuracy_score(y_test,log_predicted))
print('Confusion Matrix: \n', confusion_matrix(y_test,log_predicted))
print('Classification Report: \n', classification_report(y_test,log_predicted))

sns.heatmap(confusion_matrix(y_test,log_predicted),annot=True,fmt="d")


# In[84]:


#2) LinearSVC classifier
SVC = LinearSVC()
# Train the model using the training sets and check score
SVC.fit(X_train, y_train)
#Predict Output
SVC_predicted= SVC.predict(X_test)

SVC_score = round(SVC.score(X_train, y_train) * 100, 2)
SVC_score_test = round(SVC.score(X_test, y_test) * 100, 2)
#Equation coefficient and Intercept
print('SVC Regression Training Score: \n', SVC_score)
print('SVC Regression Test Score: \n', SVC_score_test)
print('Accuracy: \n', accuracy_score(y_test,SVC_predicted))
print('Confusion Matrix: \n', confusion_matrix(y_test,SVC_predicted))
print('Classification Report: \n', classification_report(y_test,SVC_predicted))

sns.heatmap(confusion_matrix(y_test,SVC_predicted),annot=True,fmt="d")


# In[85]:


#3) Gradient Boosting Classifier
Gradient = GradientBoostingClassifier()
# Train the model using the training sets and check score
Gradient.fit(X_train, y_train)
#Predict Output
Gradient_predicted= Gradient.predict(X_test)

Gradient_score = round(Gradient.score(X_train, y_train) * 100, 2)
Gradient_score_test = round(Gradient.score(X_test, y_test) * 100, 2)
#Equation coefficient and Intercept
print('Gradient Boosting Training Score: \n', Gradient_score)
print('Gradient Boosting Test Score: \n', Gradient_score_test)
print('Accuracy: \n', accuracy_score(y_test,Gradient_predicted))
print('Confusion Matrix: \n', confusion_matrix(y_test,Gradient_predicted))
print('Classification Report: \n', classification_report(y_test,Gradient_predicted))

sns.heatmap(confusion_matrix(y_test,Gradient_predicted),annot=True,fmt="d")


# In[86]:


#4) Decision Tree Classifier
Decider = DecisionTreeClassifier()
# Train the model using the training sets and check score
Decider.fit(X_train, y_train)
#Predict Output
Decider_predicted= Decider.predict(X_test)

Decider_score = round(Decider.score(X_train, y_train) * 100, 2)
Decider_score_test = round(Decider.score(X_test, y_test) * 100, 2)
#Equation coefficient and Intercept
print('Deision Tree Training Score: \n', Decider_score)
print('Decision Tree Test Score: \n', Decider_score_test)
print('Accuracy: \n', accuracy_score(y_test,Decider_predicted))
print('Confusion Matrix: \n', confusion_matrix(y_test,Decider_predicted))
print('Classification Report: \n', classification_report(y_test,Decider_predicted))

sns.heatmap(confusion_matrix(y_test,Decider_predicted),annot=True,fmt="d")


# In[87]:


#5) Random Forest Classifier
Random = RandomForestClassifier()
# Train the model using the training sets and check score
Random.fit(X_train, y_train)
#Predict Output
Random_predicted= Random.predict(X_test)

Random_score = round(Random.score(X_train, y_train) * 100, 2)
Random_score_test = round(Random.score(X_test, y_test) * 100, 2)
#Equation coefficient and Intercept
print('Random Forest Training Score: \n', Random_score)
print('Random Forest Test Score: \n', Random_score_test)
print('Accuracy: \n', accuracy_score(y_test,Random_predicted))
print('Confusion Matrix: \n', confusion_matrix(y_test,Random_predicted))
print('Classification Report: \n', classification_report(y_test,Random_predicted))

sns.heatmap(confusion_matrix(y_test,Random_predicted),annot=True,fmt="d")


# Decision tree worked best, so lets see what the tree looks like

# In[88]:


from sklearn.externals.six import StringIO  
from IPython.display import Image  
from sklearn.tree import export_graphviz
import pydotplus
from sklearn.feature_extraction import DictVectorizer
dot_data = StringIO()

export_graphviz(Decider, out_file=dot_data,  
                filled=True, rounded=True,
                special_characters=True, feature_names = list(X_train))

graph = pydotplus.graph_from_dot_data(dot_data.getvalue())  
Image(graph.create_png())

