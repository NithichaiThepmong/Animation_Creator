class Amaker:
    def __init__(self, width, height):
        self.width = width
        self.height = height
        self.frame = []
        self.curr_frame = 0
        self.max_frame = 100
        self.mark = "#"
        self.blank = " "

        for i in range(self.max_frame):
            self.frame.append([])
            for j in range(self.height):
                self.frame[i].append([])
                for k in range(self.width):
                    self.frame[i][j].append(self.blank)
                    
    def add_tile(self, x, y):
        self.frame[self.curr_frame][y - 1][x - 1] = self.mark

    def remove_tile(self, x, y):
        self.frame[self.curr_frame][y - 1][x - 1] = self.blank

    def move_tile(self, x1, y1, x2, y2):
        self.frame[self.curr_frame][y2 - 1][x2 - 1] = self.frame[self.curr_frame][y1 - 1][x1 - 1]
        self.frame[self.curr_frame][y1 - 1][x1 - 1] = self.blank
        
    def add_frame(self):
        if self.curr_frame < self.max_frame - 1:
            frame2 = []
            for i in range(self.height):
                frame2.append([])
                for j in range(self.width):
                    frame2[i].append([])
                    if self.frame[self.curr_frame][i][j] == self.mark:
                        frame2[i][j] = self.mark
                    else:
                        frame2[i][j] = self.blank
            self.curr_frame += 1
            self.frame[self.curr_frame] = frame2

    def remove_frame(self):
        if self.curr_frame > 0:
            for i in range(self.height):
                for j in range(self.width):
                    self.frame[self.curr_frame][i][j] = self.blank
            self.curr_frame -= 1
            
    def display_current_frame(self):
        for i in range(self.height):
            text = ""
            for j in range(self.width):
                text += self.frame[self.curr_frame][i][j]
            print(text)
        print("-----")

    def display_frame(self, count):
        if count <= self.max_frame :
            for i in range(self.height):
                text = ""
                for j in range(self.width):
                    text += self.frame[count-1][i][j]
                print(text)
            print("-----")
        
    def get_total_number_of_frames(self):
        return len(self.frame)
        
    def get_total_number_of_tiles(self):
        num_tile = 0
        for j in range(self.height):
            for i in range(self.width):
                if self.pos[j][i] == self.mark:
                    num_tile += 1
        return num_tile
        
def main():
    a = Amaker(18, 10)

    a.add_tile(4, 1)
    a.add_tile(6, 1)
    a.add_tile(10, 1)
    a.add_tile(4, 3)

    a.remove_tile(10, 1)

    a.display_current_frame()

    a.add_frame()

    a.move_tile(6, 1, 6, 2)

    a.display_current_frame()
    
    print('Exit')
if __name__ == "__main__":
    main()