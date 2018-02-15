

#import "DatabaseViewController.h"

@interface DatabaseViewController ()

@end

@implementation DatabaseViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    NSString *docsDir;
    NSArray *dirPaths;

    // Get the documents directory
    dirPaths = NSSearchPathForDirectoriesInDomains(
      NSDocumentDirectory, NSUserDomainMask, YES);

    docsDir = dirPaths[0];

    // Build the path to the database file
    _databasePath = [[NSString alloc]
       initWithString: [docsDir stringByAppendingPathComponent:
       @"book_1.db"]];

    
    NSLog(@"\nDBPath %@\n", docsDir);

    
    
    NSFileManager *filemgr = [NSFileManager defaultManager];

    if ([filemgr fileExistsAtPath: _databasePath ] == NO)
    {
       const char *dbpath = [_databasePath UTF8String];

       if (sqlite3_open(dbpath, &_bookDB) == SQLITE_OK)
       {
            char *errMsg;
            const char *sql_stmt =
           "CREATE TABLE IF NOT EXISTS CONTACTS_1 (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, LOCATION TEXT, ADDRESS TEXT, PHONE TEXT, EMAIL TEXT)";

            if (sqlite3_exec(_bookDB, sql_stmt, NULL, NULL, &errMsg) != SQLITE_OK)
            {
                 _status.text = @"Failed to create table";
            }
            sqlite3_close(_bookDB);
        } else {
                 _status.text = @"Failed to open/create database";
        }
     }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)saveData:(id)sender {
     sqlite3_stmt    *statement;
     const char *dbpath = [_databasePath UTF8String];

     if (sqlite3_open(dbpath, &_bookDB) == SQLITE_OK)
     {

           NSString *insertSQL = [NSString stringWithFormat:
             @"INSERT INTO CONTACTS_1 (name, location, address, phone, email) VALUES (\"%@\", \"%@\", \"%@\",\"%@\",\"%@\")",
             _name.text, _location.text,_address.text, _phone.text,_email_id.text];

           const char *insert_stmt = [insertSQL UTF8String];
           sqlite3_prepare_v2(_bookDB, insert_stmt,
             -1, &statement, NULL);
            if (sqlite3_step(statement) == SQLITE_DONE)
            {
                 _status.text = @"Book details added";
                 _name.text = @"";
                _location.text=@"";
                 _address.text = @"";
                 _phone.text = @"";
                _email_id.text=@"";
                
            } else {
                 _status.text = @"Failed to add details";
            }
            sqlite3_finalize(statement);
            sqlite3_close(_bookDB);
    }

}

- (IBAction)findContact:(id)sender {
     const char *dbpath = [_databasePath UTF8String];
     sqlite3_stmt    *statement;

     if (sqlite3_open(dbpath, &_bookDB) == SQLITE_OK)
     {
             NSString *querySQL = [NSString stringWithFormat:
               @"SELECT location, address, phone,email FROM contacts_1 WHERE name=\"%@\"",
               _name.text];

             const char *query_stmt = [querySQL UTF8String];

             if (sqlite3_prepare_v2(_bookDB,
                 query_stmt, -1, &statement, NULL) == SQLITE_OK)
             {
                     if (sqlite3_step(statement) == SQLITE_ROW)
                     {
                         NSString *locationField = [[NSString alloc]
                                                 initWithUTF8String:(const char *)
                                                 sqlite3_column_text(statement, 0)];
                         _location.text =locationField;
                             NSString *addressField = [[NSString alloc]
                                initWithUTF8String:
                                (const char *) sqlite3_column_text(
                                  statement, 1)];
                             _address.text = addressField;
                             NSString *phoneField = [[NSString alloc]
                                 initWithUTF8String:(const char *)
                                 sqlite3_column_text(statement, 2)];
                             _phone.text = phoneField;
                         NSString *emailField = [[NSString alloc]
                                                 initWithUTF8String:(const char *)
                                                 sqlite3_column_text(statement, 3)];
                         _email_id.text = emailField;
                             _status.text = @"Match found";
                     } else {
                             _status.text = @"Match not found";
                             _address.text = @"";
                             _phone.text = @"";
                     }
                     sqlite3_finalize(statement);
             }
             sqlite3_close(_bookDB);
     }

}

- (IBAction)clearFields:(id)sender {

    _name.text = @"";
    _location.text=@"";
    _address.text = @"";
    _phone.text = @"";
    _email_id.text=@"";
    _status.text=@"Text Fields Cleared";

}
@end
