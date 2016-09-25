using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
using System.Collections;

namespace Airport
{
    public partial class Form1 : Form
    {
        int g = 0;

        public Form1()
        {
            InitializeComponent();
            tableGrid.Columns.Add("0", "Id");
            tableGrid.Columns.Add("1", "Фамилия");
            tableGrid.Columns.Add("2", "Отчество");
            tableGrid.Columns.Add("3", "Дата рождения");
            tableGrid.Columns.Add("4", "Мили");
            tableGrid.Columns.Add("5", "Страна");
            tableGrid.Visible = false;
           

        }

        private void button1_Click(object sender, EventArgs e)
        {
            Deletetable();
        }
        private void button2_Click(object sender, EventArgs e)
        {
            Deletetable();
        }

        

        private void button3_Click(object sender, EventArgs e)
        {
            if (g < 1)
            {
                g = 1;
                tableGrid.Visible = true;
                MySqlCommand command = new MySqlCommand();
                string connectionString, commandString;
                connectionString = "Data source=127.0.0.1; UserId=root; Password=;database=mydb;";
                MySqlConnection connection = new MySqlConnection(connectionString);
                commandString = "SELECT * FROM mydb.strana;";
                command.CommandText = commandString;
                command.Connection = connection;
                MySqlDataReader reader;
                Hashtable codes = new Hashtable();


                try
                {
                    command.Connection.Open();
                    reader = command.ExecuteReader();
                    while (reader.Read())
                    {
                        codes.Add(reader["Strana_id"], reader["Nazvanie"]);
                        //codes.Add(reader["Nazvanie"], reader["ISO_3166"]);


                    }
                    reader.Close();
                }
                catch (MySqlException ex)
                {
                    Console.WriteLine("Error: \r\n{0}", ex.ToString());
                }
                finally
                {
                    command.Connection.Close();
                }


                commandString = "SELECT * FROM mydb.klient;";
                command.CommandText = commandString;
                command.Connection = connection;
                try
                {
                    command.Connection.Open();
                    reader = command.ExecuteReader();
                    while (reader.Read())
                    {
                        int rowNumber = tableGrid.Rows.Add();
                        tableGrid.Rows[rowNumber].Cells[0].Value = reader["Imja"];
                        tableGrid.Rows[rowNumber].Cells[1].Value = reader["Familiya"];
                        tableGrid.Rows[rowNumber].Cells[2].Value = reader["Otshestvo"];
                        tableGrid.Rows[rowNumber].Cells[3].Value = reader["Data_rogdeniya"];
                        tableGrid.Rows[rowNumber].Cells[4].Value = reader["Mili"];
                        tableGrid.Rows[rowNumber].Cells[5].Value = codes[reader["Strana_id"]];
                    }
                    reader.Close();
                }
                catch (MySqlException ex)
                {
                    Console.WriteLine("Error: \r\n{0}", ex.ToString());
                }
                finally
                {
                    command.Connection.Close();
                }
                codes.Clear();
            }
            
      
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
           

        }
        private void Deletetable()
        {

            while (tableGrid.Rows.Count > 1)
                for (int i = 0; i < tableGrid.Rows.Count - 1; i++)
                    tableGrid.Rows.Remove(tableGrid.Rows[i]);
            g = 0;
            tableGrid.Visible = false;
        }

    }
}
