<?php

namespace sitato\Http\Controllers;

use sitato\kendaraan;
use Illuminate\Http\Request;

class kendaraanController extends Controller
{
    public function index() 
    {
        $data= kendaraan::all();
        return response()->json($data, 200);
    }

    public function create()
    {
        //
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new kendaraan;
        $data->no_plat_kendaraan= $request->no_plat_kendaraan;
        $data->merek_kendaraan= $request->merek_kendaraan;
        $data->jenis_kendaraan= $request->jenis_kendaraan;
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
        $data= kendaraan::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(kendaraan $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= kendaraan::find($id);
        if(!is_null($request->no_plat_kendaraan))
        {
            $data->no_plat_kendaraan= $request->no_plat_kendaraan;
        }
        if(!is_null($request->merek_kendaraan))
        {
            $data->merek_kendaraan= $request->merek_kendaraan;
        }
        if(!is_null($request->jenis_kendaraan))
        {
            $data->jenis_kendaraan= $request->jenis_kendaraan;
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
        $data= kendaraan::find($id);
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
