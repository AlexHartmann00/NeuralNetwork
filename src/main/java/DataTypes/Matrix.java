package DataTypes;

public class Matrix
{
    int nrow, ncol;
    float[][] values;

    public Matrix(int rows, int cols) {
        nrow = rows;
        ncol = cols;
        values = new float[rows][cols];
    }

    public static Matrix fromStringArray(String[] ary,int r,int c)
    {
        Matrix m = new Matrix(r, c);
        for(int i = 0; i < r; i++)
        {
            System.out.println("Splitting " + ary[i] + " by ;s");
            String[] vals = ary[i].split(";");
            for(int j = 0; j < c; j++)
            {
                vals[j] = vals[j].replace(',', '.');
                m.set(i, j, Float.parseFloat(vals[j]));
            }
        }
        return m;
    }

    public void initRandom()
    {
        for (int i = 0; i < nrow; i++)
        {
            for (int j = 0; j < ncol; j++)
            {
                values[i][j] = 0.3f*(float)Math.random() - 0.15f;
            }
        }
    }

    public Matrix transpose()
    {
        Matrix t = new Matrix(ncol, nrow);
        for (int i = 0; i < nrow; i++)
        {
            for (int j = 0; j < ncol; j++)
            {
                t.set(j, i, values[i][j]);
            }
        }
        return t;
    }

    public void set(int i, int j, float v)
    {
        values[i][j] = v;
    }

    public float get(int i, int j)
    {
        return values[i][j];
    }

    public int rows()
    {
        return nrow;
    }

    public int cols()
    {
        return ncol;
    }

    public static Matrix dot(Matrix m1, Matrix m2)
    {
        if (m1.cols() == m2.rows())
        {
            int sharedDim = m1.cols();
            Matrix ret = new Matrix(m1.rows(), m2.cols());
            for (int i = 0; i < m1.rows(); i++)
            {
                for (int j = 0; j < m2.cols(); j++)
                {
                    float sum = 0;
                    for (int k = 0; k < sharedDim; k++)
                    {
                        sum += m1.get(i, k) * m2.get(k, j);
                    }
                    ret.set(i, j, sum);
                }
            }
            return ret;
        }
        else throw new IllegalArgumentException("Dimensions do not match!, " + m1.shape() + " ; " + m2.shape());
    }

    public static Matrix multiply(Matrix m1, Matrix m2)
    {
        Matrix r = new Matrix(m1.rows(), m1.cols());
        if (m1.rows() == m2.rows() && m1.cols() == m2.cols())
        {
            for (int i = 0; i < m1.rows(); i++)
            {
                for (int j = 0; j < m2.cols(); j++)
                {
                    r.set(i, j, m1.get(i, j) * m2.get(i, j));
                }
            }
            return r;
        }
        else throw new IllegalArgumentException("Dimensions do not match");
    }

    public static Matrix add(Matrix m1, Matrix m2)
    {
        Matrix r = new Matrix(m1.rows(), m1.cols());
        if (m1.rows() == m2.rows() && m1.cols() == m2.cols())
        {
            for (int i = 0; i < m1.rows(); i++)
            {
                for (int j = 0; j < m2.cols(); j++)
                {
                    r.set(i, j, m1.get(i, j) + m2.get(i, j));
                }
            }
            return r;
        }
        else throw new IllegalArgumentException("Dimensions do not match");
    }

    public Matrix add(Matrix m2)
    {
        Matrix r = new Matrix(this.rows(), this.cols());
        if (this.rows() == m2.rows() && this.cols() == m2.cols())
        {
            for (int i = 0; i < this.rows(); i++)
            {
                for (int j = 0; j < m2.cols(); j++)
                {
                    r.set(i, j, this.get(i, j) + m2.get(i, j));
                }
            }
            return r;
        }
        else throw new IllegalArgumentException("Dimensions do not match");
    }

    public Matrix timesScalar(float s)
    {
        Matrix m = new Matrix(nrow, ncol);
        for(int i = 0; i < nrow; i++)
        {
            for(int j = 0; j < ncol; j++)
            {
                m.set(i, j, values[i][j] * s);
            }
        }
        return m;
    }

    public String shape()
    {
        return "(" + nrow + "," + ncol + ")";
    }

    public String[] toStringArray()
    {
        String[] s = new String[nrow];
        for(int i = 0; i < nrow; i++)
        {
            for (int j = 0; j < ncol; j++)
            {
                s[i] += values[i][j];
                if (j != ncol - 1) s[i] += ";";
            }
        }
        return s;
    }

    public void addOnesColumn()
    {
        float[][] newvals = new float[nrow][1 + ncol];
        //ones
        for(int i = 0; i < nrow; i++)
        {
            newvals[i][0] = 1;
        }
        for(int i = 0; i < nrow; i++)
        {
            for(int j = 0; j < ncol; j++)
            {
                newvals[i][j + 1] = values[i][j];
            }
        }
        ncol++;
        values = newvals;
    }

    public Matrix skipFirstColumn()
    {
        Matrix m = new Matrix(nrow, ncol - 1);
        for(int i = 0;i < nrow; i++)
        {
            for(int j = 0; j < ncol-1; j++)
            {
                m.set(i, j, values[i][ j + 1]);
            }
        }
        return m;
    }
}
