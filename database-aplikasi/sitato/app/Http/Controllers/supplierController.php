<?php

namespace sitato\Http\Controllers;

use sitato\supplier;
use Illuminate\Http\Request;

class supplierController extends Controller
{
    public function index() 
    {
        $data= supplier::all();
        return response()->json($data, 200);
    }

    public function create()
    {
        //
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new supplier;
        $data->nama_supplier= $request->nama_supplier;
        $data->no_telepon_supplier= $request->no_telepon_supplier;
        $data->alamat_supplier= $request->alamat_supplier;
        $data->nama_sales= $request->nama_sales;
        
        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error saving', 500);
        }
        else
        {
            return response()->json('Success', 200);
        }
    }

    public function show($id) 
    {
        $data= supplier::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(supplier $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= supplier::find($id);
        if(!is_null($request->nama_supplier))
        {
            $data->nama_supplier= $request->nama_supplier;
        }
        if(!is_null($request->no_telepon_supplier))
        {
            $data->no_telepon_supplier= $request->no_telepon_supplier;
        }
        if(!is_null($request->alamat_supplier))
        {
            $data->alamat_supplier= $request->alamat_supplier;
        }
        if(!is_null($request->nama_sales))
        {
            $data->nama_sales= $request->nama_sales;
        }

        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error updating', 500);
        }
        else
        {
            return response()->json('Success updating', 200);
        }
    }

    public function destroy($id)
    {
        $data= supplier::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }

        $success= $data->delete();
        if(!$success)
        {
            return response()->json('Error deleting', 500);
        }
        else
        {
            return response()->json('Success deleting', 200);
        }
    }
}
