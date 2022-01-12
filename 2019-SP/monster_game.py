import random

class Room:
    """Describes a room
    public attributes:
        room_description (str)
        room_num (int)
    private attributes:
        exit_list (list-of-Rooms)
    """

    def set_exit_list(self, new_exit_list):
        """Sets the exit_list of self to new_exit_list
        Room, list -> None"""
        self.__exit_list = new_exit_list

    def __init__(self, room_description, room_num):
        """Initialize self
        Room, int, str -> None"""
        self.room_description = room_description
        self.room_num = room_num
        self.set_exit_list([])

    def get_exit_list(self):
        """Returns the exit_list of self
        Room -> list"""
        return self.__exit_list
        

    def __repr__(self):
        """Returns a string of the form:
        Room(description, room_num)
        Room -> str"""
        return "Room(" + repr(self.room_description) + ", " \
               + repr(self.room_num) + ")"

    def connect_to(self, addition):
        """Takes another Room or an iterable of Rooms and adds connections
        between them. Adds Rooms to the exit lists of eachother.
        self -> None"""
        if isinstance(addition, Room):
            self.set_exit_list(self.get_exit_list() + [addition])
            addition.set_exit_list(addition.get_exit_list() + [self])
        else:
            self.set_exit_list(self.get_exit_list() + addition)
            for item in addition:
                item.set_exit_list(item.get_exit_list() + [self])

    def get_exit_number_list(self):
        """Returns a list of the room numbers of adjacent rooms to self.
        Room -> list"""
        exit_number_list = []
        for item in self.get_exit_list():
            exit_number_list.append(item.room_num)
        return exit_number_list

    def description(self):
        """Returns a description of a self (a Room). Will include the name of
        the Room and a list of the room numbers of adjacent rooms.
        Room -> str"""
        return "\nYou are in the " + self.room_description + "." \
               + " Valid exits: " + str(self.get_exit_number_list())

#IN-GAME ROOM OBJECTS

foyer = Room("foyer", 1)
sitting_room = Room("sitting room", 2)
dining_room = Room("dining room", 3)
kitchen = Room("kitchen", 4)
ballroom = Room("ballroom", 5)
secret_hallway = Room("secret hallway", 6)

foyer.connect_to([sitting_room, ballroom, dining_room])
sitting_room.connect_to([dining_room, kitchen])
dining_room.connect_to([kitchen, ballroom])
kitchen.connect_to(secret_hallway)
ballroom.connect_to(secret_hallway)

game_map_rooms = [foyer, sitting_room, dining_room, kitchen, ballroom, secret_hallway]

class Command:
    """Describes movement
    public attributes:
        action_type (str)
        destination (Room)
    """

    def __init__(self, action_type, destination):
        self.action_type = action_type
        self.destination = destination

    def __repr__(self):
        return "Command(" + repr(self.action_type) + ", " \
               + repr(self.destination) + ")"

class Moveable:
    """Represents objects and characters that have changable locations.
    non-public attribute:
        location
    """
    def move_to(self, new_location):
        """sets the location to new_location
        Moveable, Room -> None"""
        self.__location = new_location

    def __init__(self, location):
        """Initialize self
        Moveable, Room -> None"""
        self.move_to(location)

    def get_location(self):
        """Returns location of self
        Moveable -> Room"""
        return self.__location
    
    def __repr__(self):
        """Returns a string of the form:
        Moveable(Room)
        Moveable -> str
        """
        return "Moveable(" + repr(self.get_location()) + ")"

    def update(self):
        """Updates location of Moveable
        Moveable -> None"""
        pass
 
class Wanderer(Moveable):
    """Represents Moveables that move randomly on their own.
    public attributes:
    is_awake (bool)
    is_shot (bool)
    """

    def __init__(self, location = None, is_awake = False, is_shot = False):
        """Initialize self
        Moveable, bool, bool -> None"""
        self.is_awake = is_awake
        self.is_shot = is_shot
        if location is not None:
            super().__init__(location)
        else:
            super().move_to(random.choice(game_map_rooms))

    def __repr__(self):
        """Returns a string of the form:
        Wanderer(location, is_awake, is_shot)
        Wanderer -> str"""
        return "Wanderer(" + repr(super().get_location()) + ", " \
               + repr(self.is_awake) + ", " + repr(self.is_shot) + ")"

    def update(self):
        """Updates the location of Wanderer
        Wanderer -> None"""
        if not self.is_awake:
            pass
        elif self.is_shot:
            pass
        else:
            self.move_to(random.choice(self.get_location().get_exit_list()))
            self.is_awake = False

monster = Wanderer()

class Player(Moveable):
    """Represents a Moveable that the user controls
    public attributes:
        dart_count (int)
    """

    def __init__(self, dart_count, location = None):
        """Initialize self
        Moveable, int -> None"""
        self.dart_count = dart_count
        Player_initial_rooms = game_map_rooms.copy()
        Player_initial_rooms.remove(monster.get_location())
        if location is not None:
            super().__init__(location)
        else:
            super().move_to(random.choice(Player_initial_rooms))

    def __repr__(self):
        """Represents the Player in a string of the form:
        Player(dart_count, location)
        Player -> str"""
        return "Player(" + repr(self.dart_count) + ", " \
               + repr(super().get_location()) + ")"

    def shoot_into(self, destination):
        """Shoots a player dart into a room
        Player, Room -> bool"""
        self.dart_count = self.dart_count - 1
        if destination == monster.get_location():
            monster.is_shot = True
            return True
        else:
            return False

    def get_command(self):
        """Will communicate with the user to figure out what the user wants to
        do and will return an appropriate Command object once a valid command
        had been entered
        Player -> Command"""
        
        room_nums = []
        for Room_item in game_map_rooms:
            room_nums.append(Room_item.room_num)
        current_location_exit_nums = self.get_location().get_exit_number_list()

        while True:
            print("What would you like to do?")
            print("""\nEnter 'shoot into NUMBER' or 'go to NUMBER'""")
            user_input = input(">> ")
            if user_input[:6].lower() == "go to ":
                try:
                    user_room_num = int(user_input[6:])
                    if user_room_num not in room_nums:
                        print("That room does not exist")
                    elif user_room_num not in current_location_exit_nums:
                        print("There is no exit to that room")
                    else:
                        for Room_item in game_map_rooms:
                            if Room_item.room_num == user_room_num:
                                return Command("move", Room_item)
                except ValueError:
                    print("I don't recognize that room number")
            elif user_input[:11].lower() == "shoot into ":
                try:
                    user_room_num = int(user_input[11:])
                    if self.dart_count == 0:
                        print("You do not have enough darts")
                    elif user_room_num not in room_nums:
                        print("That room does not exist")
                    elif user_room_num not in current_location_exit_nums:
                        print("There is no exit to that room")
                    else:
                        for Room_item in game_map_rooms:
                            if Room_item.room_num == user_room_num:
                                return Command("shoot", Room_item)
                except ValueError:
                    print("I don't recognize that room number")
            else:
                print("that wasn't a valid answer")
                
    def execute_command(self, Command_object):
        """Takes a Command object and carries out the corresoponding action.
        Command -> None"""
        if Command_object.action_type == "move":
            self.move_to(Command_object.destination)
            print("You move into room " + str(self.get_location().room_num) + ".")
            if monster.get_location() == self.get_location():
                pass
            elif monster.get_location() in self.get_location().get_exit_list():
                print("You can smell the monster, it must be nearby.")
            else:
                print("It doesn't seem like the monster is nearby.")
        else:
            self.shoot_into(Command_object.destination)
            print("You load your rifle and steady your hand.")
            print("*BANG*")
            print("A dart flies into room " \
                      + str(Command_object.destination.room_num) + "...")
            if monster.get_location() != Command_object.destination:
                print("You hear your dart whistle through the doorway and " \
                      + "then skid across the floor. The monster wasn't there.")
                print("You can hear the monster rousing and moving to a new room.")

    def update(self):
        """Returns current description of Player
        Player -> str"""
        print(self.get_location().description())
        print("You have " + str(self.dart_count) + " darts left.\n")
        new_command = self.get_command()
        self.execute_command(new_command)

#IN-GAME MOVEABLE OBJECTS

player1 = Player(2)
UPDATE_LIST = [player1, monster]

#GAME SCRIPT

print("*** WELCOME TO MONSTER CAPTURE! ***")
print("\nA wild monster is on the loose in the famous mansion on the hill. It's rich widowed owner is offering large sums of her fortune to the hunter that can catch the beast. The town's best adventurers have tried and failed to capture the beast without being eaten. There are only two tranquilizer shots left in the widow's stores...are you up to the challenge?")    

print("\nYou accept the widow's offer. She brings you to the gate of her home and warns you of what's ahead:")

print("\nYour goal is to tranqulize the monster. The mansion is large and you will have to move around to find the beast. However, if you're both in the same room, the monster will eat you; fortunate and unfortunately for you, it gives off a disgusting smell. If the monster is in an adjecent room, you will be able to smell that it is nearby. You can choose to shoot into one of the rooms with one of your two tranquilizer shots. Just be careful, because if you miss, the monster will hear your rifle and move to a new room!")

if monster.get_location() in player1.get_location().get_exit_list():
    print("\nYou begin the game in room " \
          + str(player1.get_location().room_num) \
          + ". You can smell the monster, it must be nearby.")
else:
    print("\nYou begin the game in room " \
          + str(player1.get_location().room_num) \
          + ". It doesn't seem like the monster is nearby.")

while True:
    if player1.get_location() == monster.get_location():
        print("\nYou feel the hairs on your arms raise, but there's only an abondoned room ahead of you. You take another step into the area, and that's when you hear it. Two long sets of claws scraping against the cold marble.")
        print("\nYou panic and stumble back, racing to the previous room, only to be met with two glowing eyes. There is a scream and then quiet. You have joined the unfortunate band of adventurers as another victim of the beast.")
        print("\n\n\nYou have died. Thank you for playing!")
        break
    elif monster.is_shot:
        print("\nA screech cries out from the darkness, and then you " \
              + "hear the thump of a large body.")
        print("\nYou've done it! You've captured the monster!")
        print("\nYou inch toward the beast and grab it by the slinky tail. You pull it behind you with all your might, retracing your steps through the mansion. As you run your shoulder into the large oak double door entrance, you are met with sunlight and the stony widow. She asks,")
        print("""\n"Is it done?" """)
        print("\nShe already knows, but you still drag the rest of the beast out from the mansion as proof. She eyes it cooly.")
        print("""\n"Good. If you ever want another job, this home isn't my only concern. There are other 'monsters' all across this area." """)
        print("\nAs she says this, she gives you your pay and a letter. She leaves you with one last thought")
        print("""\n"Read it when you get a chance." """)
        print("\n\n\nYou've won! Thank you for playing!")
        break
    else:
        player1.get_location().description()
        for item in UPDATE_LIST:
              item.update()

print("\n\n\nWhen you are finished playing, press any key and then enter:")
user_end_input = input(">> ")
