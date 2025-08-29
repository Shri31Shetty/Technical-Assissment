CREATE TABLE Trades (
    TradeID INT PRIMARY KEY IDENTITY(1,1),
    TradeDate DATE NOT NULL,
    Counterparty VARCHAR(100) NOT NULL,
    Commodity VARCHAR(50) NOT NULL,      
    Volume DECIMAL(10,2) NOT NULL,        
    Price DECIMAL(10,2) NOT NULL,       
    TradeType VARCHAR(10) CHECK (TradeType IN ('BUY','SELL'))
);