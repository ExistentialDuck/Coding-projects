#Import matplotlib and numpy
import matplotlib.pyplot as plt
import numpy as np
#Initialize random seed
np.random.seed(123)
#Initialize all walk list
all_walks = []

# Simulate random walk 500 times
for i in range(500) :
    random_walk = [0]
    for x in range(100) :
        step = random_walk[-1]
        dice = np.random.randint(1,7)
        if dice <= 2:
            step = max(0, step - 1)
        elif dice <= 5:
            step = step + 1
        else:
            step = step + np.random.randint(1,7)
        if np.random.rand() <= 0.001 :
            step = 0
        random_walk.append(step)
    all_walks.append(random_walk)

# Create and plot transpose of all walk list for plotting
np_aw_t = np.transpose(np.array(all_walks))

# Create ends variable to make list of final position of each random walk
ends = np_aw_t[-1]

# Plot histogram of ends, display plot, used to visualize probability of being at, or above, step 60 at the end of all random walks
plt.hist(ends)
plt.show()